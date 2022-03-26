package application;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import jdbc.SQLManager;
import pojos.Emergency;

public class ex {

	public static void main(String[] args) {
		SQLManager m = new SQLManager();
		
		Emergency u = m.Search_stored_emergency_by_code(249);
		System.out.println(u.toString());
		
		
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.getKieClasspathContainer();
		KieSession ksession = kc.newKieSession("displayKS");
		ksession.insert(u);
		ksession.fireAllRules();
		ksession.dispose();
		
		
		System.out.println(u.toString());
		
		
	}
	
}
