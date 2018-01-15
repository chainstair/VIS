//
// Created by David on 08.01.2018.
//
#pragma once
#include <string>

using namespace std;

#ifndef EX01_SENSORTYPES_H
#define EX01_SENSORTYPES_H

class Sensortypes {
public:

    //returns the sensor data as string
    virtual string toString() = 0;
};

#endif //EX01_SENSORTYPES_H