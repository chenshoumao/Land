package com.solar.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import difflib.Delta;
import difflib.DiffRow;
import difflib.DiffRowGenerator;
import difflib.DiffUtils;
import difflib.Patch;
import difflib.DiffRow.Tag;

public class CompareUtil{
	
 
	
	public static void main(String[] args) throws IOException {
		
		List<String> original = FileUtils.readLines(new File("D:/etl_org.sql"));
		List<String> revised = FileUtils.readLines(new File("D:/etl_change.sql"));

		Patch patch = DiffUtils.diff(original, revised);

//		List list = new ArrayList();
//		list.add(1);list.add(2);list.add(3);
//		list.add(2, 4);
//		for(Object ob:list){
//			System.out.println(ob);
//		}
		
		
//		for(Delta delta : patch.getDeltas()) {
//			List<?> list = delta.getOriginal().getLines();
//			for(Object object : list) {
//				System.out.println(object);
//			}
//		}
//		System.out.println("======================");
		for(Delta delta : patch.getDeltas()) {
			List<?> list = delta.getRevised().getLines();
			int count = 0;
			for(Object object : list) {
				
				int index = delta.getRevised().getPosition() + count++;
				//System.out.println(index + "," + object);
			//	original.add(index, (String)object);
			}
		}
		
//		int count = 1;
//		for(String line:original){
//			System.out.println(count++ + " " + line);
//		}
//		
		DiffRowGenerator.Builder builder = new DiffRowGenerator.Builder();
		builder.showInlineDiffs(false);
	 
		
		
		DiffRowGenerator generator = builder.build();
		for (Delta delta :  patch.getDeltas()) {
			
			List<DiffRow> generateDiffRows = generator.generateDiffRows((List<String>) delta.getOriginal().getLines(), (List<String>) delta
					.getRevised().getLines());
			int leftPos = delta.getOriginal().getPosition();
			int rightPos = delta.getRevised().getPosition();
			int count = 0;
			for (DiffRow row : generateDiffRows) {
				Tag tag = row.getTag(); 
			    if (tag == Tag.CHANGE) {
//					System.out.println("change: ");
//					System.out.println("old-> " + row.getOldLine());
//					System.out.println("new-> " + row.getNewLine());
//					System.out.println("");
					int index = delta.getOriginal().getPosition();
					original.set(index, row.getNewLine());
			    }
			}
			
			for (DiffRow row : generateDiffRows) {
				Tag tag = row.getTag(); 
				if (tag == Tag.INSERT) {
//					System.out.println("Insert: ");
//					System.out.println("new-> " + row.getNewLine());
//					System.out.println(""); 
//					int index = delta.getRevised().getPosition() + count++;
//					original.add(index,row.getNewLine());
				}
			}
			
			for (DiffRow row : generateDiffRows) {
				Tag tag = row.getTag();
				 
				if (tag == Tag.DELETE) {
//					System.out.println("delete: ");
//					System.out.println("old-> " + delta.getOriginal().getPosition() + "," + row.getOldLine());
//					System.out.println("");
					int index = delta.getOriginal().getPosition();
					//original.remove(index);
				}
			}
		}
		
		for(String line:original){
			System.out.println(line);
		}
	}
}
