from datetime import datetime

from bson import ObjectId

from database.utils import connectMongoWorker, insertDocument, mongo_item_convert
from database.utils import connection

MESSAGES_COLLECTION = "messages"


def insert_message(sender_id, receiver_id, message):
    dbclient = connectMongoWorker(connection)

    document = {
        "sender_id": sender_id,
        "receiver_id": receiver_id,
        "message": message,
        "timestamp": datetime.now()
    }
    insertDocument(document=document,
                   dbname="pos",
                   dbcollection="messages",
                   dbclient=dbclient)


def get_all_messages():
    dbclient = connectMongoWorker(connection)
    items = list(map(mongo_item_convert, [item for item in dbclient["pos"][MESSAGES_COLLECTION].find()]))
    return items


def get_message_by_id(id):
    dbclient = connectMongoWorker(connection)
    msg_coll = dbclient["pos"][MESSAGES_COLLECTION]
    message = msg_coll.find_one(filter={"_id": ObjectId(id)})
    message = mongo_item_convert(message)
    return message


def post_messages(data):
    dbclient = connectMongoWorker(connection)
    msg_coll = dbclient["pos"][MESSAGES_COLLECTION]
    _id = msg_coll.insert_one(data)
    return str(_id.inserted_id)


def put_message(id, data):
    dbclient = connectMongoWorker(connection)
    dbclient["pos"][MESSAGES_COLLECTION].update_one(filter={"_id": ObjectId(id)}, update={"$set": data}, upsert= True)


def delete_message(id):
    dbclient = connectMongoWorker(connection)
    dbclient["pos"][MESSAGES_COLLECTION].delete_one(filter={"_id": ObjectId(id)})


