//
// Created by David on 08.01.2018.
//

#include "Sensortypes.h"

#ifndef EX01_AIRSENSOR_H
#define EX01_AIRSENSOR_H


class AirSensor: public Sensortypes {
public:
    int mAir;
    int mPollution;
    
    AirSensor();
    ~AirSensor();
    
    //returns AirSensor data
    virtual string toString();
    
    //returns sensortype
    virtual string getName();
};


#endif //EX01_AIRSENSOR_H
