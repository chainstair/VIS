//
// Created by David on 08.01.2018.
//

#include "Sensortypes.h"

#ifndef EX01_LIGHTSENSOR_H
#define EX01_LIGHTSENSOR_H


class LightSensor: public Sensortypes {
public:
    int mLight;

    LightSensor();
    ~LightSensor();

    //returns light data
    virtual string toString();

    //returns sensortype
    virtual string getName();
};


#endif //EX01_LIGHTSENSOR_H
