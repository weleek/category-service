#!/bin/bash

MODE=$1

echo ------------------- CATEGORY SERVICE COMMAND -------------------

PID=`ps -ef | grep java | grep category-service | awk '{print $2}'`

if [[ "${MODE}" = "build" ]]; then
  sh category-service/gradlew bootJar -p ./category-service/
  echo 'category-service build complete!'

elif [[ "${MODE}" = "start" ]]; then

  if [[ -z "$PID" ]]; then
    echo "[ERROR] category-service process is already start ..."
  else
    sh java -jar ./category-service/build/libs/category-service-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
    ehco 'category-service start!'
  fi

elif [[ "${MODE}" = "stop" ]]; then

  if [[ -z "$PID" ]]; then
    echo "[ERROR] category-service process is not working ..."
  else
    kill -9 $PID
    ehco 'category-service stop!'
  fi

elif [[ "${MODE}" = "restart" ]]; then
  if [[ ! -z "$PID" ]]; then
    kill -9 $PID
    ehco 'category-service stop!'
  fi

  sh java -jar ./category-service/build/libs/category-service-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
  ehco 'category-service restart complete!'

else
  echo "Incorrect Parameter!!"
fi
