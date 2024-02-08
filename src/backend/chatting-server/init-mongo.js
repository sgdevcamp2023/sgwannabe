db.createCollection("chatting");

db.createUser(
    {
        user: "chatting-admin",
        pwd: "JMTpdsu8YkLEX4RA",
        roles: [
            {
                role: "readWrite",
                db: "chatting"
            }
        ]
    }
);
