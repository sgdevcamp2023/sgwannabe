db.createCollection("chart");

db.createUser(
    {
        user: "chipmunk",
        pwd: "JMTpdsu8YkLEXBW4RA",
        roles: [
            {
                role: "readWrite",
                db: "chart"
            }
        ]
    }
);
