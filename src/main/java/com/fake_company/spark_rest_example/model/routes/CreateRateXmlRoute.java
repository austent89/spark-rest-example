package com.fake_company.spark_rest_example.model.routes;

import com.fake_company.spark_rest_example.model.ApiResponse;
import com.fake_company.spark_rest_example.model.rate.Rates;
import com.fake_company.spark_rest_example.repository.ParkingRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateRateXmlRoute implements Route {

    private final ParkingRepository parkingRepository;

    public CreateRateXmlRoute(final ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public Object handle(final Request request, final Response response) throws Exception {
        final XmlMapper xmlMapper = new XmlMapper();
        final Rates rates = xmlMapper.readValue(request.body(), Rates.class);
        rates.forEach(parkingRepository::persistRate);
        response.status(200);
        return new ApiResponse(ApiResponse.ResponseStatus.Success, "Rates Created");
    }
}
