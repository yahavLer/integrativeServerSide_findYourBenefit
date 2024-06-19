package Application._a_Presentation;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Application.business_logic.BoundaryObject;
import Application.business_logic.ServicesObject;

@RestController
@RequestMapping(path = { "/superapp/objects" })
public class ControllerObject {
	private ServicesObject servicesObject;
	
	public ControllerObject(ServicesObject servicesObject) {
		this.servicesObject = servicesObject;
	}

	@PostMapping(
		consumes = MediaType.APPLICATION_JSON_VALUE, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryObject CreateAnObject(@RequestBody BoundaryObject message) {
		return this.servicesObject.createObject(message);			
		
	}

	//get specific object updated
	@GetMapping(
		path = { "/{superapp}/{id}" }, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryObject RetrieveAnObject ( 
			@PathVariable("superapp") String superapp , 
			@PathVariable("id") String id,
			@RequestParam("userSuperapp") String userSuperapp , 
			@RequestParam("email") String email) {
		
		Optional<BoundaryObject> demoOp = this.servicesObject
			.getSpecificObj(id ,superapp  , userSuperapp , email);
		
		if (demoOp.isPresent()) {
			return demoOp.get();
		}else {
			throw new BoundaryIsNotFoundException("could not find object by id: " + id);
		}
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryObject[] GetAllObjects(
			@RequestParam("userSuperapp") String userSuperapp , 
			@RequestParam("email") String email,	
			@RequestParam("size") int size , 
			@RequestParam("page") int page
			) 
	{
		
		String id_user = email+"_"+userSuperapp;
		return this.servicesObject
			.getAllObjects(id_user, size , page)
			.toArray(new BoundaryObject[0]);
	}
	
	@GetMapping(
			path = {"/search/byType/{type}?userSuperapp={superapp}&userEmail={email}&size={size}&page={page}"},
			produces = MediaType.APPLICATION_JSON_VALUE)
		public BoundaryObject[] searchObjectsByType (
			@PathVariable("type") String type , 
			@RequestParam("superapp") String superapp ,
			@RequestParam("email") String email, 
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
			BoundaryObject[] demoOp = this.servicesObject
				.searchByType(type, size, page).toArray(new BoundaryObject[0]);
			return demoOp;
		}	

	@GetMapping(
			path = {"/search/byAlias/{alias}?userSuperapp={superapp}&userEmail={email}&size={size}&page={page}"}, 
			produces = MediaType.APPLICATION_JSON_VALUE)
		public BoundaryObject[] searchObjectsByExactAlias ( 
				@PathVariable("alias") String alias , 
				@RequestParam("superapp") String superapp , 
				@RequestParam("email") String email, 
				@RequestParam(name = "size", required = false, defaultValue = "10") int size,
				@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
				BoundaryObject[] demoOp = this.servicesObject
					.searchByAlias(alias, size, page).toArray(new BoundaryObject[0]);
				return demoOp;
		}	
	@GetMapping(
			path = { "/search/byAliasPattern/{pattern}?userSuperapp={superapp}&userEmail={email}&size={size}&page={page}"}, 
			produces = MediaType.APPLICATION_JSON_VALUE)
		public BoundaryObject[] searchObjectsByAliasPattern ( 
				@PathVariable("pattern") String pattern , 
				@RequestParam("superapp") String superapp , 
				@RequestParam("email") String email, 
				@RequestParam("email") String superAppUser, 
				@RequestParam(name = "size", required = false, defaultValue = "10") int size,
				@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
				BoundaryObject[] demoOp = this.servicesObject
					.searchObjectsByAliasPattern(pattern, size, page).toArray(new BoundaryObject[0]);
				return demoOp;
		}
	
	@GetMapping(
			path = { "/search/byLocation/{lat}/{Ing}/{distance}?units={distanceUnits}&userSuperapp={superapp} &userEmail-{email}&size={size}&page={page}"},
			produces = MediaType.APPLICATION_JSON_VALUE)
		public BoundaryObject[] getObjectsInRadius (
			@PathVariable("lat") double lat , 
			@PathVariable("lat") double lng , 
			@PathVariable("distance") double distance ,
			@RequestParam(name = "distanceUnits", required = false, defaultValue = "km") String distanceUnits, 
			@RequestParam("superapp") String superapp , 
			@RequestParam("email") String email,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
			BoundaryObject[] demoOp = this.servicesObject
				.searchByLocation(lat, lng, distance, size, page).toArray(new BoundaryObject[0]);
			return demoOp;
		}	


	@PutMapping(
		path = {"/{superapp}/{id}"},
		consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateObject (
			@PathVariable("id") String id,
			@PathVariable("superapp") String superapp,
			@RequestBody BoundaryObject update , 
			@RequestParam("userSuperapp") String userSuperapp , 
			@RequestParam("email") String email) {
		
		this.servicesObject
			.updateObj(id,superapp,update ,email , userSuperapp );
	}
	
}




