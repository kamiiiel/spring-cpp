#ifndef _BEANFACTORY_H_
#define _BEANFACTORY_H_
#include "DisplayWeather.h"
#include "LocalWeatherService.h"
#include "RemoteWeatherService.h"
#include "InternetClient.h"
class BeanFactory {
public:
  BeanFactory();
  virtual void* getBean(char* name);
  virtual void start();
  virtual void stop();
  virtual ~BeanFactory();
  DisplayWeather* getDisplayWeather();
  LocalWeatherService* getMyLocalWeatherService();
  RemoteWeatherService* getMyRemoteWeatherService();
  InternetClient* getMyInternetClient();
protected:
  DisplayWeather* displayWeather;
  LocalWeatherService* myLocalWeatherService;
  RemoteWeatherService* myRemoteWeatherService;
  InternetClient* myInternetClient;
};
#endif // _BEANFACTORY_H_

