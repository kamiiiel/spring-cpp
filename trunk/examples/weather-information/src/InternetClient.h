/*
 * InternetClient.h
 *
 *  Created on: Sep 24, 2009
 *      Author: woj
 */

#ifndef INTERNETCLIENT_H_
#define INTERNETCLIENT_H_

class InternetClient {
public:

    void downloadData();

    int getNumThreads() const
    {
        return numThreads;
    }

    void setNumThreads(int numThreads)
    {
        this->numThreads = numThreads;
    }

protected:

    int numThreads;
};

#endif /* INTERNETCLIENT_H_ */
