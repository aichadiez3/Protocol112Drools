package drools.symptoms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import pojos.Disease;
import pojos.Emergency;
import pojos.Location;
import pojos.Patient;
import pojos.Protocol;
import pojos.Specialty;

public class examp {
	 public static void main(String[] args) {
		 
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.getKieClasspathContainer();
		
		execute(ks, kc);
		 
		
	 }
	 
	 public static void execute(KieServices ks, KieContainer kc) {
		 
		 KieSession ksession = kc.newKieSession("exampKS");
		 
		 Emergency u = new Emergency();
	    	u.setId(0);
	        
		    Patient p = new Patient("John","Tudor","Male", "Child", true,false,"23355");
				p.setId(0);
			u.setPatient(p);
		 
		Specialty sp1 = new Specialty(0,"Cardiology_spe");
		Disease d1 = new Disease(0,"Heart attack");
		Disease d2 = new Disease(1,"Heart failure");
		Disease d3 = new Disease(2,"Hipertensive crisis");
		Disease d4 = new Disease(3,"Ictus");
		Disease d5 = new Disease(4,"Syncope");
		
		Location w1 = new Location(0, "Work place");
		u.setLocation(w1);
		
		List<Disease> diseases = new ArrayList<>();
		
		String symp = "CHEST PRESSURE,FATIGUE,PAIN EXTENDS TO LEFT ARM,DISNEA,COLD SWEAT";
		List<String> list_1 = Arrays.asList(symp.split(","));
		List<String> symptoms_list =  new ArrayList<String>(list_1); 
			// This allows to remove or add symptoms to the list of Strings (USEFUL FOR THE APPLICATION, NOT THE RULES)
		
		d1.setSymptomsList(symptoms_list);
			diseases.add(d1);
			diseases.add(d2);
			diseases.add(d3);
			diseases.add(d4);
			diseases.add(d5);
		sp1.setDisease_list(diseases);
		
		Protocol p1 = new Protocol(0,"Go to Urgencies unit for valoration",Protocol.Type.SHIPMENT);
		List<Protocol> protocol_list = new ArrayList<>();
		protocol_list.add(p1);
		u.setProtocol_list(protocol_list);
		// STEPS
		// 1. WE SPECIFY THE SPECIALITY

			u.setSpecialty(sp1);

			
		// 2. WE SPECIFY THE SYMPTOMS OF THIS CASE (NOT THE OVERALL FROM ABOVE)
			// In this case I will choose the ones from 'onco_symptoms_list5_2' that correspond to index 9 of the disease_list
			

			Disease select = sp1.getDisease_list().get(0);  //We do this to only specify a single symptom list associated to 1 RULE and 1 DISEASE

			u.setDisease(select);
			
			
			System.out.println("BEFORE\n" +  u);
			
			ksession.insert(u);
			
			
			ksession.fireAllRules();
			System.out.println("AFTER\n" +  u);
			
			ksession.dispose();
	}
}
