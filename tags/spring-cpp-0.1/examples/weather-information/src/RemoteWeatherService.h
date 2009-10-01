/*
 * RemoteWeatherService.h
 *
 *  Created on: Sep 24, 2009
 *      Author: woj
 */

#ifndef REMOTEWEATHERSERVICE_H_
#define REMOTEWEATHERSERVICE_H_

#include "IWeatherService.h"
#include "InternetClient.h"

class RemoteWeatherService: public IWeatherService {
public:

    virtual void getWeather();
    char *getHostname() const
    {
        return hostname;
    }

    void setHostname(char *hostname)
    {
        this->hostname = hostname;
    }

    InternetClient *getInternetClient() const
    {
        return internetClient;
    }

    void setInternetClient(InternetClient *internetClient)
    {
        this->internetClient = internetClient;
    }


protected:

    InternetClient* internetClient;
    char* hostname;
};

#endif /* REMOTEWEATHERSERVICE_H_ */
