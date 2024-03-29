package api.garage;

import api.mappings.Car;

import api.mappings.Human;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import retrofit2.Response;



import static api.retrofit.vehicle.Vehicles.*;
import static api.retrofit.vehicle.Vehicles.deleteVehicleById;
import static api.validators.ResponseValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class AddVehicleToClientPositiveTest {

        private Integer vehicleId, clientId;
        @BeforeTest
        public void setup(){
            Human ClientResponse = Human.builder()
                    .firstName("James")
                    .lastName("Bond")
                    .phoneNumber(915035235)
                    .address("MI - 6 Londres")
                    .country("Inglaterra")
                    .city("Londres")
                    .postalCode("4350-334")
                    .nif(233211225)
                    .birthDate("2024-01-18")
                    .clientDate("2024-01-13")
                    .build();
            Response<Integer> responseHuman = createClient(ClientResponse);
            assertThat("Body is not null", responseHuman.body(), notNullValue());
            clientId = responseHuman.body();


            Car carRequest = Car.builder()
                    .brand("Renault")
                    .model("Megane")
                    .plateYear(2019)
                    .type("Van")
                    .plate("AB-22-WW")
                    .active(true)
                    .build();
            Response<Integer> responseCreate = createVehicles(carRequest);

            assertThat("Body is not null", responseCreate.body(), notNullValue());
            vehicleId = responseCreate.body();
        }

        @Test(description = "add a client to a vehicle with sucess")
        public void addVehicleToClientTest(){
            Response<Integer> response = addVehicleToClient(vehicleId, clientId);
            assertNoContent(response);

            Car carResponse = getVehicleById(vehicleId).body();

            Human humanResponse = getClientById(clientId).body();

            assertThat("id should not be null", carResponse.getId(), notNullValue());
            assertThat("Client should not be null", carResponse.getClient(), is(humanResponse.getId()));
            assertThat("Brand should not be expected", carResponse.getBrand(), is(carResponse.getBrand()));
            assertThat("Model should not be expected", carResponse.getModel(), is(carResponse.getModel()));
            assertThat("PlateYear should not be expected", carResponse.getPlateYear(), is(carResponse.getPlateYear()));
            assertThat("Type should not be expected", carResponse.getType(), is(carResponse.getType()));
            assertThat("PLate should not be expected", carResponse.getPlate(), is(carResponse.getPlate()));
            assertThat("Active should not be expected", carResponse.isActive(), is(carResponse.isActive()));
        }

        @AfterMethod
        public void cleanUp(){
            deleteVehicleById(vehicleId);

            deleteClientById(clientId);
    }
}
