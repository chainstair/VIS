//
// Created by David on 08.01.2018.
//

#include <string.h>
#include "Sensortypes.h"

#ifndef EX01_NOISESENSOR_H
#define EX01_NOISESENSOR_H


class NoiseSensor: public Sensortypes {
public:
    int mNoise;

    NoiseSensor();
    ~NoiseSensor();

    //returns noise data
    virtual string toString();

    //returns sensortype
    virtual string getName();
};


#endif //EX01_NOISESENSOR_H
