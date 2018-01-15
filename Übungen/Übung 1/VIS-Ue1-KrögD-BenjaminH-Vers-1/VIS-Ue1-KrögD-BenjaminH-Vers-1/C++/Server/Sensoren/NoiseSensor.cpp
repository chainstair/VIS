//
// Created by David on 08.01.2018.
//

#include "NoiseSensor.h"

NoiseSensor::NoiseSensor() {
    mNoise = rand() %150;
}

NoiseSensor::~NoiseSensor() {

}

string NoiseSensor::toString() {
    string noise = to_string(mNoise);

    string returnString = "";
    returnString.append(noise);

    return returnString;
}

string NoiseSensor::getName() {
    return "noise";
}
