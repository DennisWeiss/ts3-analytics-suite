# TS3 Social AI

## Currently Implemented:

- Writing users data, including IP address, country, long, lat, etc., into a MariaDB.
- Fetching data of currently visiting users and channel ID's every 10 seconds
- Storing them in a user_in_channel table
- Simple REST Controller with plain information about users

## TODO:

- Building a small AI that finds out how close one user is related to another one
- Building website, where the following data is depicted:
    - TS3 server usage (amount of users connected),
    - their origins (including heatmap),
    - user relations visually depicted as a weighted graph
    