# TS3 Analytics Suite

[![Build Status](https://travis-ci.com/DennisWeiss/ts3-analytics-suite.svg?branch=master)](https://travis-ci.com/DennisWeiss/ts3-analytics-suite)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/5897a483bb39421482d91e27747d1898)](https://www.codacy.com/app/DennisWeiss/ts3-analytics-suite?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=DennisWeiss/ts3-analytics-suite&amp;utm_campaign=Badge_Grade)

## Currently Implemented:

- Writing users data, including IP address, country, long, lat, etc., into a MariaDB.
- Fetching data of currently visiting users and channel ID's every 10 seconds
- Storing them in a user_in_channel table
- Simple REST Controller with plain information about users
- small AI that finds out how close one user is related to another one
- Web Interface, where the following data is depicted:
    - user relations visually depicted as a weighted graph
	- user data overview (basic info) and their estimated locations
	- locations of all users are shown in a heatmap

## TODO:

- TS3 server usage (amount of users connected),
    
