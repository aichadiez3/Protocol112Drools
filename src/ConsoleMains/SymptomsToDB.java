package ConsoleMains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import jdbc.SQLManager;
import pojos.Symptom;

public class SymptomsToDB {
	private static SQLManager controller;
	
	public static void main(String[] args) {
		controller = new SQLManager();
		controller.Connect();
        Boolean tables = controller.Create_tables();
        
        if(tables==true) {
        	controller.Insert_default_elements_toDB();
        }
	
	
		List<String> cardio_symps_1 = Arrays.asList("Chest pressure/Fatigue/Pain extends to the left arm/Disnea/Cold sweat/|".split("/"));
		List<String> cardio_symps_2 = Arrays.asList("Phlegm/Swelling/Faints/Fatigue/Heart palpitations/|".split("/"));
		List<String> cardio_symps_3 = Arrays.asList("Swelling/Faints/Fatigue/|".split("/"));
		List<String> cardio_symps_4 = Arrays.asList("Sudden numbness/Paralysis/Confusion/Difficulty speaking or undestand/Loss of vision/Loss of balance/Pain/|".split("/"));
		List<String> cardio_symps_5 = Arrays.asList("Pale skin/Daze/Tunnel vision/Warmth sensation/Cold sweat/|".split("/"));

		
		List<String> names = new ArrayList<>();
			names.addAll(cardio_symps_1);
			names.addAll(cardio_symps_2);
			names.addAll(cardio_symps_3);
			names.addAll(cardio_symps_4);
			names.addAll(cardio_symps_5);
		
		System.out.println(names);
		
		List<Symptom> cardio_symp_list = new ArrayList<>();
		int m=0;
		for(String n : names) {
			
			if(m < names.size()) {
				cardio_symp_list.add(new Symptom(m, n));
				m++;
			} else {
				break;
			}
			
		}
		System.out.println(cardio_symp_list);
		
		Boolean comp=false;
		// Assosiate to disease
		
		List<String> cardio_disease_list = Arrays.asList("Heart attack,Heart failure,Hipertensive crisis,Ictus,Syncope".split(","));
		
		
		Integer i = 0;
		for (String d: cardio_disease_list) {
			
			List<String> saver = new ArrayList<>(); 
			
			Integer index = controller.Insert_new_disease(d, controller.Search_specialty_id_by_name("Cardiology"));
			
			if(cardio_symp_list.get(i).toString().contains("|")) {
				
				System.out.println("element | detected");
				i++;
			} 
			
			while(!cardio_symp_list.get(i).toString().contains("|")) {

				saver.add(cardio_symp_list.get(i).getSymptom());
				i++;
				System.out.println("#"+i);
				
				
				
				
			}
			
			comp = controller.Associate_symptom_list_to_disease(saver.toString(), controller.Search_disease_by_id(index).getId());
			saver.clear();
			}
					
				
		System.out.println(comp);
	
	}
	
	
}
