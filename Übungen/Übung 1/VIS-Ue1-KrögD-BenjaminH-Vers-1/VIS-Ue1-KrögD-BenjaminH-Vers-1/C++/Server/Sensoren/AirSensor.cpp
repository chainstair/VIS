//
// Created by David on 08.01.2018.
//

#include "AirSensor.h"

AirSensor::AirSensor() {
    mAir = rand() % 150;
    mPollution = rand() %150;
}

AirSensor::~AirSensor() {

}

string AirSensor::toString() {
    string air = to_string(this->mAir);
    string pollution = to_string(this->mPollution);
    
    string returnString = "";
    returnString.append(air);
    returnString.append(";");
    returnString.append(pollution);
    
    return returnString;
}

string AirSensor::getName() {
    return "air";
}
