package com.vehicle.review.integration;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(tags= {"@registration","@addAutoMobile","@editMobileNo","@removeAutoMobile"},plugin= {"pretty"})
public class CucumberIntegration {
 
}
