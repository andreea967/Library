from database.utils import connectMongoWorker, insertDocument, mongo_item_convert

from database.utils import connection

USERS_COLLECTION = "users"
# POST
def insert_user(uid, wishList, favorites):
    dbclient = connectMongoWorker(connection)

    document = {
        "uid": uid,
        "wishList": wishList,
        "favorites": favorites
    }
    insertDocument(document=document,
                   dbname="pos",
                   dbcollection="users",
                   dbclient=dbclient)
    return uid


def get_user_by_id(id: int):
    dbclient = connectMongoWorker(connection)
    user = dbclient["pos"][USERS_COLLECTION].find_one(filter={"uid": id})
    if user is not None:
        user = mongo_item_convert(user)
    return user


def post_user(data):
    dbclient = connectMongoWorker(connection)
    usr_coll = dbclient["pos"][USERS_COLLECTION]
    _id = usr_coll.insert_one(data)
    return str(_id.inserted_id)


def put_user(id, data):
    dbclient = connectMongoWorker(connection)
    dbclient["pos"][USERS_COLLECTION].update_one(filter={"uid": id}, update={"$set": data}, upsert=True)

def delete_user(id):
    dbclient = connectMongoWorker(connection)
    dbclient["pos"][USERS_COLLECTION].delete_one(filter={"uid": id})


