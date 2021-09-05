# Project description

The reactive spring boot weather app. Uses JWT token for authorization, webflux and r2dbc driver(Reactive analog of JDBC) for non blocking requests. All migrations by flyway, placed in resources/db/migration. Database PostgreSQL.

# Authorization

You can register new user via /lk/register method. It will return JWT token in json format response. After, you can authorize with /lk/login method. For REST testing you may use user@email.com login and 2783200 password or admin@email.com login and 2783200 password for admin panel testing

sample registration request:

```
{
    "login": "alenovich255@gmail.com",
    "password": "2783200a",
    "fullname": "Alen Rasulov"
}
```

sample authorization request:
```
{
    "username": "user@email.com",
    "password": "2783200"
}
```

# User API

Use the package manager [pip](https://pip.pypa.io/en/stable/) to install foobar.

## /api/cities-list
GET method, returns all cities in JSON format

## /api/subscribe-to-city
POST method to subscribe user to selected city, sample request:

```
{
    "cityToSubscribe": 1 //city id
}
```

## /api/unsubscribe
POST method to unsubscribe user from selected city, sample request:

```
{
    "cityToUnsubscribe": 1 //city id
}
```

## /api/update-city-weather
GET method to get actual weather for cities which user subscribed

# Admin API

## /admin/user-list
GET method to get all users

## /admin/user-details?userId=123(Optional, may be null)
GET method to get active subscriptions of selected users, or all users if userId params is not null

## /admin/edit-user
POST method to edit user, sample request:

```
{
    "userId": 19,
    "fullname": "Test Test", //optional
    "roleId": 1 //optional
}
```

## /admin/cities-list
GET method to get all cities

## /admin/edit-city
POST method for edit or create new city, sample request

```
{
    "cityId": 1, //Optional, it will create new city if cityId is null
    "name": "Bukhara",
    "cityDesc": "Some description of this city"
}
```

## /admin/update-city-weather
POST method to set actual city weather for selected city, sample request:

```
{
    "cityId": 1,
    "degreesCelsius": 37.8 -- Temperature in degrees celsius
    "windSpeedMPS" -- Wind speed in meter per second
}
```