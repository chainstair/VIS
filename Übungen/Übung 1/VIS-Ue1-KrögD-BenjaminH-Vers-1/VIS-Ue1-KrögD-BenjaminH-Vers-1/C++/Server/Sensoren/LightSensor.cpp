//
// Created by David on 08.01.2018.
//

#include "LightSensor.h"

LightSensor::LightSensor() {
    mLight = rand() %150;
}

LightSensor::~LightSensor() {

}

string LightSensor::toString() {
    string light = to_string(mLight);

    string returnString = "";
    returnString.append(light);

    return returnString;
}

string LightSensor::getName() {
    return "light";
}
