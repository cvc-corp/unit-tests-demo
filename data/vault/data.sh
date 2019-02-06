#!/bin/bash

./vault write secret/unit-tests-demo spring.datasource.password=_pass_ spring.datasource.username=_user_
