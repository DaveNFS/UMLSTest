import UtsMetathesaurusContent.ConceptDTO;
import UtsMetathesaurusContent.UtsWsContentController;
import UtsMetathesaurusContent.UtsWsContentControllerImplService;
import UtsSecurity.UtsWsSecurityController;
import UtsSecurity.UtsWsSecurityControllerImplService;


public class Client2 {

	public static void doStuff()
	{
		try {
			// Runtime properties
			String username = "chapmanww";
			String password = "InspectorChen@UMLS";
			String umlsRelease = "2012AA";
			String serviceName = "http://umlsks.nlm.nih.gov";
			
			
                        
			UtsWsContentController utsContentService = (new UtsWsContentControllerImplService()).getUtsWsContentControllerImplPort();
            UtsWsSecurityController securityService = (new UtsWsSecurityControllerImplService()).getUtsWsSecurityControllerImplPort();
            
            //get the Proxy Grant Ticket - this is good for 8 hours and is needed to generate single use tickets.
            String ticketGrantingTicket = securityService.getProxyGrantTicket(username, password);

            //build some ConceptDTOs and retrieve UI and Default Preferred Name
            
            //use the Proxy Grant Ticket to get a Single Use Ticket
            String singleUseTicket1 = securityService.getProxyTicket(ticketGrantingTicket, serviceName);
            ConceptDTO result1 =  utsContentService.getConcept(singleUseTicket1, umlsRelease, "C0018787");
            
            System.out.println(result1.getUi() );
            System.out.println(result1.getDefaultPreferredName() );
            
            //use the Proxy Grant Ticket to get another Single Use Ticket
            String singleUseTicket2 = securityService.getProxyTicket(ticketGrantingTicket, serviceName);
            ConceptDTO result2 =  utsContentService.getConcept(singleUseTicket2, umlsRelease, "C0014591");
            System.out.println(result2.getUi() );
            System.out.println(result2.getDefaultPreferredName() );
                        
                        
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	public static void doStuff2()
	{
		try {
			// Runtime properties
			String username = "chapmanww";
			String password = "InspectorChen@UMLS";
			String umlsRelease = "2012AA";
			String serviceName = "http://umlsks.nlm.nih.gov";
			
                        
			UtsWsContentController utsContentService = (new UtsWsContentControllerImplService()).getUtsWsContentControllerImplPort();
            UtsWsSecurityController securityService = (new UtsWsSecurityControllerImplService()).getUtsWsSecurityControllerImplPort();
            
            //get the Proxy Grant Ticket - this is good for 8 hours and is needed to generate single use tickets.
            String ticketGrantingTicket = securityService.getProxyGrantTicket(username, password);

            //build some ConceptDTOs and retrieve UI and Default Preferred Name
            
            //use the Proxy Grant Ticket to get a Single Use Ticket
            String singleUseTicket1 = securityService.getProxyTicket(ticketGrantingTicket, serviceName);
            ConceptDTO result1 =  utsContentService.getConcept(singleUseTicket1, umlsRelease, "C0018787");
            
            System.out.println(result1.getUi() );
            System.out.println(result1.getDefaultPreferredName() );
            
            //use the Proxy Grant Ticket to get another Single Use Ticket
            String singleUseTicket2 = securityService.getProxyTicket(ticketGrantingTicket, serviceName);
            ConceptDTO result2 =  utsContentService.getConcept(singleUseTicket2, umlsRelease, "C0014591");
            System.out.println(result2.getUi() );
            System.out.println(result2.getDefaultPreferredName() );
                        
                        
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
	
	

