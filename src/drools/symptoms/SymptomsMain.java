package drools.symptoms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import pojos.*;;

public class SymptomsMain {
	
	public static void main(String[] args) {
		
		KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();

        execute(ks, kc);
		
	}
	
	public static void execute(KieServices ks, KieContainer kc) {

		KieSession ksession = kc.newKieSession("symptomsKS");
		
		Emergency u = new Emergency();
    	u.setId(0);
        
	    Patient p = new Patient("John","Tudor","Male", "Child", true,false,"23355");
			p.setId(0);
		u.setPatient(p);
		
		/*
		 * 
		 * REMEMBER: The following objects in the App will be added automatically from Database
		 * 
		 * */
		
		Protocol p1 = new Protocol(0,"Go to Urgencies unit for valoration",Protocol.Type.SHIPMENT);
		Protocol p2 = new Protocol(1,"Connect to oxygen supply",Protocol.Type.INPLACE);
		Protocol p3 = new Protocol(2,"Take paracetamol",Protocol.Type.ADVICE);
		Protocol p4 = new Protocol(3,"Connect to oxygen supply + Inject subcutaneous antipyretic medication",Protocol.Type.SHIPMENT);
		Protocol p5 = new Protocol(4,"Make appointment with doctor for further evaluation + Take medication and consider changes in status",Protocol.Type.ADVICE);
		Protocol p6 = new Protocol(5,"Connect catheter + intravenous antifluid and pain medicaments",Protocol.Type.INPLACE);
		Protocol p7 = new Protocol(6, "Connect to oxygen supply + Control vomits + If the person begins to have seizures, administer first aid for these cases",Protocol.Type.SHIPMENT);
		Protocol p8 = new Protocol(7, "Measure constants +  Nitroglicerine + Reanimation", Protocol.Type.SHIPMENT); // heart attack
		Protocol p9 = new Protocol(8, "Measure constants +  Oxygen + Diuretics", Protocol.Type.SHIPMENT); // heart failure severe
		Protocol p10 = new Protocol(9, "Measure constants +  Oxygen", Protocol.Type.INPLACE); // heart failure light
		Protocol p11 = new Protocol(10, "Tension measure + calm the pacient + hipotensive drug", Protocol.Type.INPLACE); // hipertensive crisis
		Protocol p12 = new Protocol(11, "Head elevation + ECG + Tension measure + Neuroprotector drugs", Protocol.Type.SHIPMENT); // ictus
		Protocol p13 = new Protocol(12, "Leg lift + Hydration + ECG + Recommend to make appointment with doctor for further evaluation", Protocol.Type.INPLACE); // syncope
		Protocol p14 = new Protocol(13,"Keep the person comfortable + Give water + Ensure access to fresh air + Try to make the person calm", Protocol.Type.ADVICE);
		Protocol p15 = new Protocol(14,"Make space among person + Clear hard or sharp objects + Don't try to stop the movements + Place the person on side to clear to airways", Protocol.Type.INPLACE);
		Protocol p16 = new Protocol(15,"The fastest way to hospital", Protocol.Type.SHIPMENT);
		Protocol p17 = new Protocol(13, "Leg lift + Hydration", Protocol.Type.ADVICE); // syncope

		// p17 has same index as p14 -----> CHANGE THIS
		
		//neurology protocol
		
		

		
		//Include here more protocols from excel `add them to the correspondent list
		
		
		List<Protocol> protocol_list = new ArrayList<>();
		protocol_list.add(p1);
		protocol_list.add(p2);
		protocol_list.add(p3);
		protocol_list.add(p4);
		protocol_list.add(p5);
		protocol_list.add(p6);
		protocol_list.add(p7);
		protocol_list.add(p8);
		protocol_list.add(p9);
		protocol_list.add(p10);
		protocol_list.add(p11);
		protocol_list.add(p12);
		protocol_list.add(p13);
		protocol_list.add(p14);
		protocol_list.add(p15);
		protocol_list.add(p16);
		protocol_list.add(p17);
		

		
		u.setProtocol_list(protocol_list);

		
		
		
		Location w1 = new Location(0,"Home");
        Location w2 = new Location(1,"Transit");
        Location w3 = new Location(2,"Work place");
        Location w4 = new Location(3,"Mountain");
        Location w5 = new Location(4,"Beach");
        u.setLocation(w5);
		
		Specialty sp1 = new Specialty(0,"Cardiology");
		Specialty sp2 = new Specialty(1,"Toxicology");
		Specialty sp3 = new Specialty(2,"Neurology");
		Specialty sp4 = new Specialty(3,"Traumatology");
		Specialty sp5 = new Specialty(4,"Oncology");
		Specialty sp6 = new Specialty(5,"Other");   // Esta es a�n cuestionable
		
		List<Disease> cardio_diseases = new ArrayList<>();
		Disease cardio1 = new Disease(0,"Heart attack");
		Disease cardio2 = new Disease(1,"Heart failure");
		Disease cardio3 = new Disease(2,"Heart failure");
		Disease cardio4 = new Disease(3,"Hipertensive crisis");
		Disease cardio5 = new Disease(4,"Ictus");
		Disease cardio6= new Disease(5,"Syncope");
		Disease cardio7= new Disease(6,"Syncope");
		
		
	// Several cases for same Disease because some symptoms differ
		List<Disease> onco_diseases = new ArrayList<>();
		Disease onco1_1 = new Disease(0,"Colon cancer");
		Disease onco1_2 = new Disease(1,"Colon cancer");
		Disease onco2_1 = new Disease(2,"Prostate cancer");
		Disease onco2_2 = new Disease(3,"Prostate cancer");
		Disease onco3_1 = new Disease(4,"Lung cancer");
		Disease onco3_2 = new Disease(5,"Lung cancer");
		Disease onco4_1 = new Disease(6,"Breast cancer");
		Disease onco4_2 = new Disease(7,"Breast cancer");
		Disease onco5_1= new Disease(8,"Leukemia");
		Disease onco5_2= new Disease(9,"Leukemia");
		
		
		// Neurology diseases
		List<Disease> neuro_diseases = new ArrayList<>();
		Disease neuro1_1 = new Disease(0, "Stroke");
		Disease neuro2_1 = new Disease(1, "Epilepsy");
		Disease neuro2_2 = new Disease(2,"Epilepsy");
		Disease neuro2_3 = new Disease(3,"Epilepsy");
		
		Disease neuro3_1 = new Disease(4,"Migraine");
		Disease neuro3_2 = new Disease(5,"Migraine");
		Disease neuro4_1 = new Disease(6,"Ptsd");
		Disease neuro5_1 = new Disease(7, "Meningities");
		
		
		
		List<Disease> toxic_diseases = new ArrayList<>();
		Disease tox1_1 = new Disease(0,"Food poisoning");
		Disease tox1_2 = new Disease(1,"Food poisoning");
		Disease tox1_3 = new Disease(2,"Food poisoning");
		Disease tox1_4 = new Disease(3,"Food poisoning");
		Disease tox1_5 = new Disease(4,"Food poisoning");
		Disease tox1_6 = new Disease(5,"Food poisoning");
		Disease tox1_7 = new Disease(6,"Food poisoning");
		Disease tox2_1 = new Disease(7,"Drugs");
		Disease tox2_2 = new Disease(8,"Drugs");
		Disease tox3_1 = new Disease(9,"Hangover");
		Disease tox3_2 = new Disease(10,"Hangover");
		Disease tox3_3 = new Disease(11,"Hangover");
		Disease tox3_4 = new Disease(12,"Hangover");
		Disease tox4_1 = new Disease(13,"Allergies for medicamente");
		Disease tox4_2 = new Disease(14,"Allergies for medicamente");
		Disease tox4_3 = new Disease(15,"Allergies for medicamente");
		Disease tox4_4 = new Disease(16,"Allergies for medicamente");
		Disease tox4_5 = new Disease(17,"Allergies for medicamente");
		Disease tox4_6 = new Disease(18,"Allergies for medicamente");
		Disease tox4_7 = new Disease(19,"Allergies for medicamente");
		Disease tox4_8 = new Disease(20,"Allergies for medicamente");
		Disease tox4_9 = new Disease(21,"Allergies for medicamente");
		Disease tox4_10 = new Disease(22,"Allergies for medicamente");
		Disease tox4_11 = new Disease(23,"Allergies for medicamente");
		Disease tox4_12 = new Disease(24,"Allergies for medicamente");
		Disease tox4_13 = new Disease(25,"Allergies for medicamente");
		
		
		List<Disease> other_diseases = new ArrayList<>();
		Disease other1 = new Disease(0,"Unknown (not urgent)");
		Disease other2 = new Disease(1,"Unknown (urgent)");
		
		//Include here more diseases in a lists of correspondent specialities

		
		
		// NOW   --------->  CREATE LISTS OF SYMPTOMS (SYMPLIFY IT TO THE MAXIMUM)
			
		// CARDIOLOGY
		
		//HEART ATTACK
		String cardio_symp1 = "Chest pressure/Fatigue/Pain extends to the left arm/Disnea/Cold sweat"; 
		List<String> cardio_list_1 = Arrays.asList(cardio_symp1.split("/"));
		List<String> cardio_symptoms_list1 =  new ArrayList<String>(cardio_list_1); 
			// This allows to remove or add symptoms to the list of Strings (USEFUL FOR THE APPLICATION, NOT THE RULES)
		
		//HEART FAILURE
		String cardio_symp2 = "Phlegm/Swelling/Faints/Fatigue/Heart palpitations";
		List<String> cardio_list_2 = Arrays.asList(cardio_symp2.split("/"));
		List<String> cardio_symptoms_list2 =  new ArrayList<String>(cardio_list_2); 
		
		String cardio_symp3 = "Swelling/Faints/Fatigue";
		List<String> cardio_list_3 = Arrays.asList(cardio_symp2.split("/"));
		List<String> cardio_symptoms_list3 =  new ArrayList<String>(cardio_list_3); 
		
		//HIPERTENSIVE CRISIS
		String cardio_symp4 = "Vomits/Pain/Disnea/Confusion";
		List<String> cardio_list_4 = Arrays.asList(cardio_symp2.split("/"));
		List<String> cardio_symptoms_list4 =  new ArrayList<String>(cardio_list_4); 
		
		//ICTUS
		String cardio_symp5 = "Sudden numbness/Paralysis/Confusion/Difficulty speaking or undestand/Loss of vision/Loss of balance /Pain";
		List<String> cardio_list_5 = Arrays.asList(cardio_symp2.split("/"));
		List<String> cardio_symptoms_list5 =  new ArrayList<String>(cardio_list_5);
		
		//SYNCOPE
		String cardio_symp6 = "Pale skin/Daze/Tunnel vision/Warmth sensation/Cold sweat";
		List<String> cardio_list_6 = Arrays.asList(cardio_symp2.split("/"));
		List<String> cardio_symptoms_list6 =  new ArrayList<String>(cardio_list_6);
	
		
		cardio1.setSymptomsList(cardio_symptoms_list1);
			cardio_diseases.add(cardio1);
		cardio2.setSymptomsList(cardio_symptoms_list2);
			cardio_diseases.add(cardio2);
		cardio3.setSymptomsList(cardio_symptoms_list3);
			cardio_diseases.add(cardio3);
		cardio4.setSymptomsList(cardio_symptoms_list4);
			cardio_diseases.add(cardio4);
		cardio5.setSymptomsList(cardio_symptoms_list5);
			cardio_diseases.add(cardio5);
		cardio6.setSymptomsList(cardio_symptoms_list6);
			cardio_diseases.add(cardio6);
		cardio7.setSymptomsList(cardio_symptoms_list6);
			cardio_diseases.add(cardio7);
			
		sp1.setDisease_list(cardio_diseases);
		
		//sp1.getDisease_list().get(0);  //---> Corresponds to heart attack
		
		
		// TOXICOLOGY

		// Food Poisoning
		String tox_symp1 = "Fever/Chills/Diarrhea/Pain/Nausea/Headache/Heart rate acceleration/Skin redness/Vomits/Paralysis";
		List<String> tox_list_1 = Arrays.asList(tox_symp1.split("/"));
		List<String> tox_symptoms_list1 = new ArrayList<String>(tox_list_1);

		// Drugs
		String tox_symp2 = "Negative Mood/Vomits/Hyperactivity/Phlegmaticy/Reddened Sclera";
		List<String> tox_list_2 = Arrays.asList(tox_symp2.split("/"));
		List<String> tox_symptoms_list2 = new ArrayList<String>(tox_list_2);

		// Hangover
		String tox_symp3 = "Fatigue/Headache/Loss of appetite/Diarrhea/Nausea/Vomits/Loss of appetite/Headache";
		List<String> tox_list_3 = Arrays.asList(tox_symp3.split("/"));
		List<String> tox_symptoms_list3 = new ArrayList<String>(tox_list_3);

		// Allergies for medicamente
		String tox_symp4 = "Vomits/Runny Nose/Itching/Hives/Swelling/Skin Rash/Shortness of breath";
		List<String> tox_list_4 = Arrays.asList(tox_symp4.split("/"));
		List<String> tox_symptoms_list4 = new ArrayList<String>(tox_list_4);

		
		tox1_1.setSymptomsList(tox_symptoms_list1);
		toxic_diseases.add(tox1_1);
		tox1_2.setSymptomsList(tox_symptoms_list1);
		toxic_diseases.add(tox1_2);
		tox1_3.setSymptomsList(tox_symptoms_list1);
		toxic_diseases.add(tox1_3);
		tox1_4.setSymptomsList(tox_symptoms_list1);
		toxic_diseases.add(tox1_4);
		tox1_5.setSymptomsList(tox_symptoms_list1);
		toxic_diseases.add(tox1_5);
		tox1_6.setSymptomsList(tox_symptoms_list1);
		toxic_diseases.add(tox1_6);
		tox1_7.setSymptomsList(tox_symptoms_list1);
		toxic_diseases.add(tox1_7);
		tox2_1.setSymptomsList(tox_symptoms_list2);
		toxic_diseases.add(tox2_1);
		tox2_2.setSymptomsList(tox_symptoms_list2);
		toxic_diseases.add(tox2_2);
		tox3_1.setSymptomsList(tox_symptoms_list3);
		toxic_diseases.add(tox3_1);
		tox3_2.setSymptomsList(tox_symptoms_list3);
		toxic_diseases.add(tox3_2);
		tox3_3.setSymptomsList(tox_symptoms_list3);
		toxic_diseases.add(tox3_3);
		tox3_4.setSymptomsList(tox_symptoms_list3);
		toxic_diseases.add(tox3_4);
		tox4_1.setSymptomsList(tox_symptoms_list4);
		toxic_diseases.add(tox4_1);
		tox4_2.setSymptomsList(tox_symptoms_list4);
		toxic_diseases.add(tox4_2);
		tox4_3.setSymptomsList(tox_symptoms_list4);
		toxic_diseases.add(tox4_3);
		tox4_4.setSymptomsList(tox_symptoms_list4);
		toxic_diseases.add(tox4_4);
		tox4_5.setSymptomsList(tox_symptoms_list4);
		toxic_diseases.add(tox4_5);
		tox4_6.setSymptomsList(tox_symptoms_list4);
		toxic_diseases.add(tox4_6);
		tox4_7.setSymptomsList(tox_symptoms_list4);
		toxic_diseases.add(tox4_7);
		tox4_8.setSymptomsList(tox_symptoms_list4);
		toxic_diseases.add(tox4_8);
		tox4_9.setSymptomsList(tox_symptoms_list4);
		toxic_diseases.add(tox4_9);
		tox4_10.setSymptomsList(tox_symptoms_list4);
		toxic_diseases.add(tox4_10);
		tox4_11.setSymptomsList(tox_symptoms_list4);
		toxic_diseases.add(tox4_11);
		tox4_12.setSymptomsList(tox_symptoms_list4);
		toxic_diseases.add(tox4_12);
		tox4_13.setSymptomsList(tox_symptoms_list4);
		toxic_diseases.add(tox4_13);
	

		sp2.setDisease_list(toxic_diseases);
			
			
		// NEUROLOGY
			
			String neuro_symp1 = "Headache/Loss of vision/Loss of balance /Difficulty speaking or undestand/Leg or arm weakness";
			List<String> neuro_list_1 = Arrays.asList(neuro_symp1.split("/"));
			List<String> neuro_symptoms_list1 =  new ArrayList<String>(neuro_list_1); 
			
			String neuro_symp2 = "Staring at one point/Muscle construction/Loss of consciousness/Staring at one point";
			List<String> neuro_list_2 = Arrays.asList(neuro_symp2.split("/"));
			List<String> neuro_symptoms_list2 =  new ArrayList<String>(neuro_list_2);
			
			String neuro_symp3 = "Vomits/Headache/Fatigue/Sensitivity to light, smell or sound/Thirst /Mood changes";
			List<String> neuro_list_3 = Arrays.asList(neuro_symp3.split("/"));
			List<String> neuro_symptoms_list3 =  new ArrayList<String>(neuro_list_3);
			
			
			String neuro_symp4 = "Headache/Nausea/Pain/Chest pain/Negative Mood/Profused sweating/Difficulty breathing/Avoidence of feelings, thoughts or places";
			List<String> neuro_list_4 = Arrays.asList(neuro_symp4.split("/"));
			List<String> neuro_symptoms_list4 =  new ArrayList<String>(neuro_list_4);
			
			
			String neuro_symp5 = "Headache/Seizures/Fever/Rash/Sleepiness/Vomits/Neck or joints pain/Light sensivity";
			List<String> neuro_list_5 = Arrays.asList(neuro_symp5.split("/"));
			List<String> neuro_symptoms_list5 =  new ArrayList<String>(neuro_list_5);
			
			neuro1_1.setSymptomsList(neuro_symptoms_list1);
			neuro_diseases.add(neuro1_1);
			
			neuro2_1.setSymptomsList(neuro_symptoms_list2);
			neuro_diseases.add(neuro2_1);
			
			neuro2_2.setSymptomsList(neuro_symptoms_list2);
			neuro_diseases.add(neuro2_2);
			
			neuro2_3.setSymptomsList(neuro_symptoms_list2);
			neuro_diseases.add(neuro2_3);
			
			neuro3_1.setSymptomsList(neuro_symptoms_list3);
			neuro_diseases.add(neuro3_1);
			
			neuro3_2.setSymptomsList(neuro_symptoms_list3);
			neuro_diseases.add(neuro3_2);
			
			neuro4_1.setSymptomsList(neuro_symptoms_list4);
			neuro_diseases.add(neuro4_1);
			
			neuro5_1.setSymptomsList(neuro_symptoms_list5);
			neuro_diseases.add(neuro5_1);
			
			sp3.setDisease_list(neuro_diseases);
			
		// TRAUMATOLOGY
		
		// ONCOLOGY

			// ---------> COLON CANCER
		String onco_symp1_1 = "Constipation/Heart palpitations/Fever/Diarrhea/Pain/General malaise";
		List<String> onco_list_1_1 = Arrays.asList(onco_symp1_1.split("/"));
		List<String> onco_symptoms_list1_1 =  new ArrayList<String>(onco_list_1_1); 
		
		String onco_symp1_2 = "Constipation/Insomnia/Heart palpitations/Fever/Diarrhea/Vomits/Pain/General malaise";
		List<String> onco_list_1_2 = Arrays.asList(onco_symp1_2.split("/"));
		List<String> onco_symptoms_list1_2 =  new ArrayList<String>(onco_list_1_2); 
		
			// ---------> PROSTATE CANCER
		String onco_symp2_1 = "Dysuria/Fatigue/Pain/Fluid retention";
		List<String> onco_list_2_1 = Arrays.asList(onco_symp2_1.split("/"));
		List<String> onco_symptoms_list2_1 =  new ArrayList<String>(onco_list_2_1); 
		
		String onco_symp2_2 = "Dysuria/Fatigue/Pain/Fluid retention/Blood in urine";
		List<String> onco_list_2_2 = Arrays.asList(onco_symp2_2.split("/"));
		List<String> onco_symptoms_list2_2 =  new ArrayList<String>(onco_list_2_2); 
		
			// ---------> LUNG CANCER

		String onco_symp3_1 = "Chest pain/Fatigue/Fever/General malaise";
		List<String> onco_list_3_1 = Arrays.asList(onco_symp3_1.split("/"));
		List<String> onco_symptoms_list3_1 =  new ArrayList<String>(onco_list_3_1); 
		
		String onco_symp3_2 = "Chest pain/Fatigue/Fever/General malaise/Severe blood sputum";
		List<String> onco_list_3_2 = Arrays.asList(onco_symp3_2.split("/"));
		List<String> onco_symptoms_list3_2 =  new ArrayList<String>(onco_list_3_2); 
		
			// ---------> BREAST CANCER		
		
		String onco_symp4_1 = "Pain/Breast inflamation/Nipple discharge";
		List<String> onco_list_4_1 = Arrays.asList(onco_symp4_1.split("/"));
		List<String> onco_symptoms_list4_1 =  new ArrayList<String>(onco_list_4_1); 
		
		String onco_symp4_2 = "Pain/Breast inflamation/Nipple discharge/Skin ulcer";
		List<String> onco_list_4_2 = Arrays.asList(onco_symp4_2.split("/"));
		List<String> onco_symptoms_list4_2 =  new ArrayList<String>(onco_list_4_2); 
		
			// ---------> LEUKEMIA
		
		String onco_symp5_1 = "Fever/Severe infection/Pain/Fatigue/Sweating";
		List<String> onco_list_5_1 = Arrays.asList(onco_symp5_1.split("/"));
		List<String> onco_symptoms_list5_1 =  new ArrayList<String>(onco_list_5_1); 
		
		String onco_symp5_2 = "Fever/Pain/Fatigue/Sweating";
		List<String> onco_list_5_2 = Arrays.asList(onco_symp5_2.split("/"));
		List<String> onco_symptoms_list5_2 =  new ArrayList<String>(onco_list_5_2); 
		
		
		
		onco1_1.setSymptomsList(onco_symptoms_list1_1);
			onco_diseases.add(onco1_1);
		onco1_2.setSymptomsList(onco_symptoms_list1_2);
			onco_diseases.add(onco1_2);
		onco2_1.setSymptomsList(onco_symptoms_list2_1);
			onco_diseases.add(onco2_1);
		onco2_2.setSymptomsList(onco_symptoms_list2_2);
			onco_diseases.add(onco2_2);	
		onco3_1.setSymptomsList(onco_symptoms_list3_1);
			onco_diseases.add(onco3_1);
		onco3_2.setSymptomsList(onco_symptoms_list3_2);
			onco_diseases.add(onco3_2);	
		onco4_1.setSymptomsList(onco_symptoms_list4_1);
			onco_diseases.add(onco4_1);
		onco4_2.setSymptomsList(onco_symptoms_list4_2);
			onco_diseases.add(onco4_2);	
		onco5_1.setSymptomsList(onco_symptoms_list5_1);
			onco_diseases.add(onco5_1);
		onco5_2.setSymptomsList(onco_symptoms_list5_2);
			onco_diseases.add(onco5_2);	
			
		sp5.setDisease_list(onco_diseases);
				
		
		
		// OTHER
		
		String other_symp1 = "Fever/Fatigue/Sweating";
		List<String> other_list_1 = Arrays.asList(other_symp1.split("/"));
		List<String> other_symptoms_list1 =  new ArrayList<String>(other_list_1); 
			// This allows to remove or add symptoms to the list of Strings (USEFUL FOR THE APPLICATION, NOT THE RULES)
		
		String other_symp2 = "Fever/Pain/Fatigue/Sweating";
		List<String> other_list_2 = Arrays.asList(other_symp2.split("/"));
		List<String> other_symptoms_list2 =  new ArrayList<String>(other_list_2); 
		
		other1.setSymptomsList(other_symptoms_list1);
			other_diseases.add(other1);
		other2.setSymptomsList(other_symptoms_list2);
			other_diseases.add(other2);
		
		sp6.setDisease_list(other_diseases);
		
		
			
		// To compare the list of symptoms avoiding the order ----> Needed for the interface NOT rules
	/*
		Collections.sort(list_1);
		Collections.sort(symptoms_list);
		System.out.println(list_1.equals(symptoms_list));
	*/		
		
		
	// STEPS
	// 1. WE SPECIFY THE SPECIALITY

		u.setSpecialty(sp5);

		
	// 2. WE SPECIFY THE SYMPTOMS OF THIS CASE (NOT THE OVERALL FROM ABOVE)
		// In this case I will choose the ones from 'onco_symptoms_list5_2' that correspond to index 9 of the disease_list
		

		Disease select = sp5.getDisease_list().get(0);  //We do this to only specify a single symptom list associated to 1 RULE and 1 DISEASE

		u.setDisease(select);
		
		
		System.out.println("BEFORE\n" +  u);
		
		ksession.insert(u);
		
		
		ksession.fireAllRules();
		System.out.println("AFTER\n" +  u);
		
		ksession.dispose();
		
	}
	
}
