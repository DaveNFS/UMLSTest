import java.util.ArrayList;
import java.util.List;

import UtsMetathesaurusContent.*;
import UtsMetathesaurusContent.UtsFault_Exception;
import UtsSecurity.*;
import UtsMetathesaurusFinder.*;


public class UMLSAccess {
	String username = "atreynolds36";
	String password = "Medical2012";
	String source = "2012AA";
	String serviceName = "http://umlsks.nlm.nih.gov";
	String ticketGrantingTicket;
	String targetCUI;
	
	UtsWsSecurityController securityService;
	
	UtsWsFinderController utsFinderService;
	UtsWsContentController utsContentService;
	
	UtsMetathesaurusContent.Psf contentPSF = new UtsMetathesaurusContent.Psf();
	UtsMetathesaurusFinder.Psf finderPSF = new UtsMetathesaurusFinder.Psf();
	
	public UMLSAccess() throws UtsMetathesaurusContent.UtsFault_Exception,UtsMetathesaurusFinder.UtsFault_Exception,UtsSecurity.UtsFault_Exception{
		contentPSF.setIncludedLanguage("ENG");
/*		finderPSF.getIncludedSources().add("MEDLINEPLUS");
		finderPSF.getIncludedSources().add("ICD9CM");
		finderPSF.getIncludedSources().add("BI");
		finderPSF.getIncludedSources().add("CCPSS");
		finderPSF.getIncludedSources().add("NCI");
		contentPSF.getIncludedSources().add("MEDLINEPLUS");
		contentPSF.getIncludedSources().add("BI");
		contentPSF.getIncludedSources().add("ICD9CM");
		contentPSF.getIncludedSources().add("CCPSS");
		contentPSF.getIncludedSources().add("NCI");*/
		System.out.println(finderPSF.getIncludedSources());
		securityService = (new UtsWsSecurityControllerImplService()).getUtsWsSecurityControllerImplPort();
		ticketGrantingTicket = securityService.getProxyGrantTicket(username,password);
		utsFinderService = (new UtsWsFinderControllerImplService()).getUtsWsFinderControllerImplPort();
		utsContentService = (new UtsWsContentControllerImplService()).getUtsWsContentControllerImplPort();
	}
	
	public String getProxyTicket() throws UtsSecurity.UtsFault_Exception{
			return securityService.getProxyTicket(ticketGrantingTicket, serviceName);
	}
	public String checkForMatch(String search) throws UtsMetathesaurusFinder.UtsFault_Exception, UtsSecurity.UtsFault_Exception{
		finderPSF.setPageLn(1);
		List<UiLabel> myUiLabels = new ArrayList<UiLabel>();
		myUiLabels = utsFinderService.findConcepts(getProxyTicket(), source, "atom", search, "words", finderPSF);
		if(myUiLabels.size() != 0)
			return myUiLabels.get(0).getLabel();
		return "No Match";
	}
	public String[] searchUMLS(String searchTerm) throws UtsMetathesaurusFinder.UtsFault_Exception, UtsSecurity.UtsFault_Exception, UtsFault_Exception{
		finderPSF.setPageLn(1);
		List<UiLabel> myUiLabels = new ArrayList<UiLabel>();
		myUiLabels = utsFinderService.findConcepts(getProxyTicket(), source, "atom", searchTerm, "words", finderPSF);
		if(myUiLabels.size() != 0){
			String label = myUiLabels.get(0).getLabel();
			targetCUI = myUiLabels.get(0).getUi();
			//System.out.println(label + " " + targetCUI);
		}
		List<ConceptRelationDTO> myConceptRelationsDTO = new ArrayList<ConceptRelationDTO>();
		myConceptRelationsDTO = utsContentService.getConceptConceptRelations(getProxyTicket(), source, targetCUI,contentPSF);
		String[] results = new String[myConceptRelationsDTO.size()];
		for(int x = 0; x < myConceptRelationsDTO.size(); x++){
			ConceptRelationDTO myConceptRelationDTO = myConceptRelationsDTO.get(x);
			//String aui = myAtomDTO.getUi();
			//String source = myAtomDTO.getRootSource();
			String name = myConceptRelationDTO.getRelatedConcept().getDefaultPreferredName();
			name = name.trim();
			name = name.toLowerCase();
			//String TermType = myAtomDTO.getTermType();
			//int cvMemberCount = myAtomDTO.getCvMemberCount();
				boolean add = true;
				for(int y = 0; y < results.length; y++){
					if(results[y] == null)
						break;
					if(results[y].contains(name)){
						add = false;
						break;
					}
					//String[] punc = {",","[","]",".",",","(",")","\\","/","*","^",":"};
					//for(int g = 0 ; g<punc.length ; g++)
					//{
						//if(results[y].contains(punc[g]))
						//{
							//add = false;
							//break;
						//}
					//}
					
						
					
					if(results[y].equalsIgnoreCase(name)){
						add = false;
						break;
					}
				}
				if(add == true){
					results[x] = name;
				}
		}
/*		String[] resultsCut = new String[5];
		if(results.length > 5){
			for(int x = 0; x < 5; x++)
				resultsCut[x] = results[x];
			return resultsCut;
		}*/
		return results;
	}
	
	public String[] searchUMLS2(String searchTerm) throws UtsMetathesaurusFinder.UtsFault_Exception, UtsSecurity.UtsFault_Exception, UtsFault_Exception{
		finderPSF.setPageLn(1);
		List<UiLabel> myUiLabels = new ArrayList<UiLabel>();
		myUiLabels = utsFinderService.findConcepts(getProxyTicket(), source, "atom", searchTerm, "words", finderPSF);
		if(myUiLabels.size() != 0){
			String label = myUiLabels.get(0).getLabel();
			targetCUI = myUiLabels.get(0).getUi();
			//System.out.println(label + " " + targetCUI);
		}
		List<AtomDTO> myAtoms = new ArrayList<AtomDTO>();
		myAtoms = utsContentService.getConceptAtoms(getProxyTicket(), source, targetCUI,contentPSF);
		String[] results = new String[myAtoms.size()];
		for(int x = 0; x < myAtoms.size(); x++){
			AtomDTO myAtomsDTO = myAtoms.get(x);
			//String aui = myAtomDTO.getUi();
			//String source = myAtomDTO.getRootSource();
			String name = myAtomsDTO.getTermString().getName();
			name = name.trim();
			name = name.toLowerCase();
			//String TermType = myAtomDTO.getTermType();
			//int cvMemberCount = myAtomDTO.getCvMemberCount();
			boolean add = true;
			for(int y = 0; y < results.length; y++){
				if(results[y] == null)
					break;
				if(results[y].contains(name)){
					add = false;
					break;
				}
				if(results[y].equalsIgnoreCase(name)){
					add = false;
					break;
				}
			}
			if(add == true){
				results[x] = name;
			}
		}
		return results;
	}
	public static void main(String[] args) throws UtsFault_Exception, UtsMetathesaurusFinder.UtsFault_Exception, UtsSecurity.UtsFault_Exception{
		UMLSAccess ua = new UMLSAccess();
		ua.searchUMLS("C0014591");
	}
}
