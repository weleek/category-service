#!/bin/bash

MODE=$1
SERVICE_HOME="$( cd "$( dirname "$0" )" && pwd -P )"
PID=`ps -ef | grep java | grep category-service | awk '{print $2}'`

if [[ "${MODE}" = "build" ]]; then
  sh $SERVICE_HOME/gradlew bootJar -p $SERVICE_HOME
  echo 'category-service build complete!'

elif [[ "${MODE}" = "start" ]]; then

  # shellcheck disable=SC2236
  if [[ ! -z "$PID" ]]; then
    echo "[ERROR] category-service process is already start ..."
  else
    java -jar $SERVICE_HOME/build/libs/category-service-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
    echo 'category-service start!'
  fi

elif [[ "${MODE}" = "stop" ]]; then

  if [[ -z "$PID" ]]; then
    echo "[ERROR] category-service process is not working ..."
  else
    kill -9 $PID
    echo 'category-service stop!'
  fi

elif [[ "${MODE}" = "restart" ]]; then

  # shellcheck disable=SC2236
  if [[ ! -z "$PID" ]]; then
    kill -9 $PID
    echo 'category-service stop!'
  fi

  java -jar $SERVICE_HOME/build/libs/category-service-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
  echo 'category-service restart complete!'

else
  echo "Incorrect Parameter!!"
  echo "[Usage] sh category-service.sh [build|start|stop|restart]"
fi
