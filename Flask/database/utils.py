import sys

from pymongo import MongoClient, errors

connection = {
    "dbhost": "localhost",
    "dbport": 27017,
    "dbuser": "pos",
    "dbpasswd": "pos",
    "dbname": "pos",
}


def connectMongoWorker(mConfigData):
    dbclient = None
    try:
        dbclient = MongoClient(host=mConfigData['dbhost'],
                               port=mConfigData['dbport'],
                               username=mConfigData['dbuser'],
                               password=mConfigData['dbpasswd'],
                               authSource=mConfigData['dbname']
                               )

        #print('[MONGODB_CONNECT] listing collections for users {0:s}: {1:s}'.format(mConfigData['dbuser'], str(dbclient[mConfigData[
        #                                                                               'dbname']].list_collection_names())))
    except AttributeError as attr_error:
        print("attr_error")
        sys.exit(1)
    except errors.ConnectionFailure as mongo_conn_err:
        print("mongo_conn_error")
        sys.exit(1)
    except errors.ConfigurationError as mongo_config_err:
        print("mongo_config_error")
        sys.exit(1)
    except Exception as generic_error:
        print(generic_error)
        sys.exit(1)
    return dbclient


def insertDocument(document=None, dbname=None, dbcollection=None, dbclient=None):
    if document is None or dbname is None or dbcollection is None or dbclient is None:
        print("parametri nuli")
        return
    try:
        collection_worker = dbclient[dbname][dbcollection]
        document_id = collection_worker.insert_one(document=document)

        print('[MONGO_WORKER] New ObjectID {0:s}'.format(str(document_id.inserted_id)))

    except Exception as generic_error:
        print("generic_err")
        sys.exit(1)


def get_collections():
    dbclient = connectMongoWorker(connection)
    return dbclient["pos"].list_collection_names()


def mongo_item_convert(x: dict):
    if '_id' in x:
        x["_id"] = str(x["_id"])
    return x