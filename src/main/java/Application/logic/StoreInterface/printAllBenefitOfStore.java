package Application.logic.StoreInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import Application.business_logic.Boundaies.BoundaryCommand;
import Application.business_logic.Boundaies.BoundaryObject;
import Application.business_logic.DataService.ServicesObject;
import Application.logic.MiniappInterface;

public class printAllBenefitOfStore implements MiniappInterface {
	private ServicesObject ServicesObject;
	
	

	public printAllBenefitOfStore(Application.business_logic.DataService.ServicesObject servicesObject) {
		ServicesObject = servicesObject;
	}



	@Override
	public Object activateCommand(BoundaryCommand miniappCommandBoundary) {
		
		String storeId = miniappCommandBoundary.getTargetObject().getObjectId().getId();
		String superApp = miniappCommandBoundary.getTargetObject().getObjectId().getSuperApp();
		String userSuperapp = miniappCommandBoundary.getInvokedBy().getUserId().getSuperAPP();
		String email = miniappCommandBoundary.getInvokedBy().getUserId().getEmail();
		
		BoundaryObject boundaryObject = null;
		Optional<BoundaryObject> store = this.ServicesObject
		.getSpecificObj(storeId ,superApp  , userSuperapp , email);
		
		if (store.isPresent()) {
			boundaryObject = store.orElse(null);
		}
		
		List<Integer> benefits = getAListFromMap(boundaryObject.getObjectDetails(),"listOfBenefitOfClub");
		List<BoundaryObject> benefits_objects = new ArrayList<>();
		for (Integer benefit : benefits) {
			Optional<BoundaryObject> benefitObj =this.ServicesObject.getSpecificObj("B"+benefit, superApp, userSuperapp, email);
			if (benefitObj.isPresent()) {
				boundaryObject = benefitObj.orElse(null);
			}
			benefits_objects.add(boundaryObject);
		}
		return benefits_objects;
		
		
		
	}
	public List<Integer> getAListFromMap(Map<String, Object> objectDetails , String key)
	{
		List<Object> objects = (List)(objectDetails.get(key));
		List <Integer> numbers = 	objects.stream().map(Object::toString)
				.map(str->Integer.parseInt(str))
				.toList();
		return numbers;
	}
}
