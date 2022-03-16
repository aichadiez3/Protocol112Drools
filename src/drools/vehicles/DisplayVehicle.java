package drools.vehicles;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import pojos.*;


public class DisplayVehicle {

	/*
	 * First: A location is setted. This location is associated to a vehicle
	 * Secondly: After evaluation of the case and the severity:
	 * 
	 * 				severity 1,2,3 -> Ambulance (or other)
	 * 				severity 4,5 -> No vehicle display
	 */
	
    public static void main(String[] args) {

        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        
        execute(ks, kc);
    }

    public static void execute(KieServices ks, KieContainer kc) {
    
        KieSession ksession = kc.newKieSession("vechicleKS");
        
        // THIS PART WORKS
        
        Location w1 = new Location(0,"Home");
        Location w2 = new Location(1,"Transit");
        Location w3 = new Location(2,"Work place");
        Location w4 = new Location(3,"Mountain");
        Location w5 = new Location(4,"Beach");
         
        Emergency u = new Emergency();
        
        /*
        Specialty sp5 = new Specialty(0,"Oncology");
        Protocol prot = new Protocol(0,"info");
        	u.setId(0);
	        u.setLocation(w1);
	        u.setProtocol(prot);
	        u.setSeverity(3);
	        u.setSpecialty(sp5);
	        
	     */
        Patient p = new Patient("John","Tudor","Male", "Child", true,false,"23355");
			p.setId(0);
			u.setPatient(p);
        
		u.setLocation(w4);
		
		
		
		/*	// DON'T KNOW IF THIS IS POSSIBLE --> LEAVE IT (ask Alberto next week)
			// The finality of this is to apply directly all the rules for 1 emergency case to obtain the 'severity_case' evaluated!!
			 * 
			KieSession ksession2 = kc.newKieSession("symptomsKS");
			ksession2.insert(sp5);
			ksession2.fireAllRules();
			System.out.println("AFTER\n" + sp5);
			ksession2.dispose();
		 */
		
        System.out.println(u);
    	ksession.insert(u);
    	ksession.fireAllRules();
        
        System.out.println("AFTER");
        System.out.println(u);
        ksession.dispose();
        
        
        
    }

    
}
