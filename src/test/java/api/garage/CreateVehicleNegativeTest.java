package api.garage;

import api.mappings.Car;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import static api.retrofit.vehicle.Vehicles.*;
import static api.validators.ResponseValidator.assertBadRequest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreateVehicleNegativeTest {

    private Integer vehicleId;

    @Test(description = "create vehicle with failure")
    public void createVehicleNegativeTest(){
        Car carRequest = Car.builder()
                .id(15) // o erro é o ID, pra criar, ele tem que ser null
                .brand("Renault")
                .model("Megane")
                .plateYear(2019)
                .type("Van")
                .plate("AB-22-WW")
                .active(false)
                .build();
        Response<Integer> response = createVehicles(carRequest);
        assertBadRequest(response);

    }

    

}