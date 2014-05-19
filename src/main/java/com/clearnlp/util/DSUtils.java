/**
 * Copyright 2014, Emory University
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.clearnlp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.clearnlp.collection.list.FloatArrayList;
import com.clearnlp.util.pair.DoubleIntPair;
import com.clearnlp.util.pair.Pair;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @since 3.0.0
 * @author Jinho D. Choi ({@code jdchoi77@gmail.com})
 */
public class DSUtils
{
	private DSUtils() {}
	
	/**
	 * @param in internally wrapped by {@code new BufferedReader(new InputStreamReader(in))}.
	 * The file that the input-stream is created from consists of one entry per line. 
	 * @param decap TODO
	 */
	static public Set<String> createStringHashSet(InputStream in, boolean trim, boolean decap)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		Set<String> set = Sets.newHashSet();
		String line;

		try
		{
			while ((line = reader.readLine()) != null)
			{
				if (trim)
				{
					line = line.trim();
					if (line.isEmpty()) continue;
				}
				
				if (decap)
					line = StringUtils.toLowerCase(line);
				
				set.add(line);
			}			
		}
		catch (IOException e) {e.printStackTrace();}
		
		return set;
	}
	
	/**
	 * @param in internally wrapped by {@code new BufferedReader(new InputStreamReader(in))}.
	 * The file that the input-stream is created from consists of one entry per line ("key"<delim>"value").
	 */
	static public Map<String,String> createStringHashMap(InputStream in, CharTokenizer tokenizer, boolean trim)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		Map<String,String> map = Maps.newHashMap();
		List<String> t;
		String line;
		
		try
		{
			while ((line = reader.readLine()) != null)
			{
				if (trim)
				{
					line = line.trim();
					if (line.isEmpty()) continue;
				}
				
				t = tokenizer.tokenize(line);
				map.put(t.get(0), t.get(1));
			}			
		}
		catch (IOException e) {e.printStackTrace();}
		
		return map;
	}

	static public <T extends Comparable<? extends T>>void sortReverseOrder(List<T> list)
	{
		Collections.sort(list, Collections.reverseOrder());
	}

	static public <T>boolean hasIntersection(Collection<T> col1, Collection<T> col2)
	{
		if (col2.size() < col1.size())
		{
			Collection<T> tmp = col1;
			col1 = col2;
			col2 = tmp;
		}
		
		for (T item : col1)
		{
			if (col2.contains(item))
				return true;
		}
		
		return false;
	}

	/** @return a set containing all field values of this class. */
	static public Set<String> getFieldSet(Class<?> cs)
	{
		Set<String> set = Sets.newHashSet();
		
		try
		{
			for (Field f : cs.getFields())
				set.add(f.get(cs).toString());
		}
		catch (IllegalArgumentException e) {e.printStackTrace();}
		catch (IllegalAccessException e)   {e.printStackTrace();}
		
		return set;
	}
	
	/** @return the index'th item if exists; otherwise, {@code null}. */
	static public <T>T get(List<T> list, int index)
	{
		return isRange(list, index) ? list.get(index) : null;
	}
	
	/** @return the last item in the list if exists; otherwise, {@code null}. */
	static public <T>T getLast(List<T> list)
	{
		return list.isEmpty() ? null : list.get(list.size()-1);
	}

	static public boolean isRange(List<?> list, int index)
	{
		return 0 <= index && index < list.size();
	}

	/**
	 * @param beginIndex inclusive
	 * @param endIndex exclusive
	 */
	static public int[] range(int beginIndex, int endIndex, int gap)
	{
		double d = MathUtils.divide(endIndex-beginIndex, gap);
		if (d < 0) return new int[0];
		
		int[] array = new int[MathUtils.ceil(d)];
		int i, j;
		
		if (beginIndex < endIndex)
		{
			for (i=beginIndex,j=0; i<endIndex; i+=gap,j++)
				array[j] = i;
		}
		else
		{
			for (i=beginIndex,j=0; i>endIndex; i+=gap,j++)
				array[j] = i;
		}
	
		return array;
	}

	static public int[] range(int size)
	{
		return range(0, size, 1);
	}
	
	static public void swap(int[] array, int index0, int index1)
	{
		int tmp = array[index0];
		array[index0] = array[index1];
		array[index1] = tmp;
	}
	
	static public <T>void swap(List<T> list, int index0, int index1)
	{
		T tmp = list.get(index0);
		list.set(index0, list.get(index1));
		list.set(index1, tmp);
	}
	
	static public <T>void shuffle(int[] array, Random rand)
	{
		shuffle(array, rand, array.length);
	}
	
	/** Calls {@link #shuffle(List, Random, int)}, where {@code lastIndex = list.size()}. */
	static public <T>void shuffle(List<T> list, Random rand)
	{
		shuffle(list, rand, list.size());
	}
	
	static public <T>void shuffle(int[] array, Random rand, int lastIndex)
	{
		int i, j, size = lastIndex - 1;
		
		for (i=0; i<size; i++)
		{
			j = rand.nextInt(size - i) + i + 1;
			swap(array, i, j);
		}
	}
	
	/**
	 * A slightly modified version of Durstenfeld's shuffle algorithm.
	 * @param lastIndex shuffle up to this index (exclusive, cannot be greater than the list of the list).
	 */
	static public <T>void shuffle(List<T> list, Random rand, int lastIndex)
	{
		int i, j, size = lastIndex - 1;
		
		for (i=0; i<size; i++)
		{
			j = rand.nextInt(size - i) + i + 1;
			swap(list, i, j);
		}
	}
	
	/** Adds all items in the specific array to the specific list. */
	static public void addAll(List<String> list, String[] array)
	{
		for (String item : array)
			list.add(item);
	}
	
	static public void append(FloatArrayList list, float value, int n)
	{
		int i;
		
		for (i=0; i<n; i++)
			list.add(value);
	}
	
	static public int maxIndex(double[] array)
	{
		int i, size = array.length, maxIndex = 0;
		double maxValue = array[0];
		
		for (i=1; i<size; i++)
		{
			if (maxValue < array[i])
			{
				maxIndex = i;
				maxValue = array[i];
			}
		}
		
		return maxIndex;
	}
	
	static public Pair<DoubleIntPair,DoubleIntPair> top2(double[] array)
	{
		int i, size = array.length;
		DoubleIntPair fst, snd;
		
		if (array[0] < array[1])
		{
			fst = toDoubleIntPair(array, 1);
			snd = toDoubleIntPair(array, 0);
		}
		else
		{
			fst = toDoubleIntPair(array, 0);
			snd = toDoubleIntPair(array, 1);			
		}
		
		for (i=2; i<size; i++)
		{
			if (fst.d < array[i])
			{
				snd.set(fst.d, fst.i);
				fst.set(array[i], i);
			}
			else if (snd.d < array[i])
				snd.set(array[i], i);
		}
		
		return new Pair<DoubleIntPair,DoubleIntPair>(fst, snd);
	}
	
	static public DoubleIntPair toDoubleIntPair(double[] array, int index)
	{
		return new DoubleIntPair(array[index], index);
	}
}