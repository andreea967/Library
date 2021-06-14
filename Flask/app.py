import bson
from flask import Flask, make_response, jsonify, request
from database import utils
from database import users
from database import messages

app = Flask(__name__)


@app.route('/')
def hello_world():
    return {"message": 'Hello World!'}


@app.route('/api/pos', methods=['GET'])
def get_collections():
    collections = utils.get_collections()
    return make_response({"collections": collections}, 200)


#USERS
@app.route('/api/pos/users', methods=['GET'])
def get_all_users():
    return make_response(jsonify([]), 200)


@app.route('/api/pos/users/<id>', methods=['GET'])
def get_user_by_id(id: str):
    try:
        user = users.get_user_by_id(int(id))
        if user is not None:
            return make_response(jsonify(user), 200)
        return make_response({"msg": "Not Found"}, 404)
    except ValueError:
        return make_response({"msg": "ID must be integer"}, 401)


@app.route('/api/pos/users', methods=['POST'])
def post_user():
    data = dict(request.json)
    if "uid" not in data:
        return make_response({"msg": "error"}, 401)

    if users.get_user_by_id(data["uid"]) is not None:
        return make_response({"error": "User already exists"}, 400)

    user = users.post_user(data)
    return make_response(jsonify(user), 200)


@app.route('/api/pos/users/<id>', methods=['PUT'])
def put_user(id: str):
    try:
        data = request.json
        users.put_user(int(id), data)
        return make_response(jsonify({"id": id}), 200)
    except ValueError:
        return make_response({"err": "id must pe int"}, 401)


@app.route('/api/pos/users/<id>', methods=['DELETE'])
def delete_user(id: str):
    users.delete_user(int(id))
    return make_response("", 200)


#MESSAGE
@app.route('/api/pos/messages', methods=['GET'])
def get_all_messages():
    messes = messages.get_all_messages()
    return make_response(jsonify(messes), 200)


@app.route('/api/pos/messages/<id>', methods=['GET'])
def get_message_by_id(id: str):
    try:
        mess_id = messages.get_message_by_id(id)
        return make_response(jsonify(mess_id), 200)
    except bson.errors.InvalidId:
        return make_response({"error": "invalid id"}, 401)


@app.route('/api/pos/messages', methods=['POST'])
def post_messages():
    data = dict(request.json)
    if "sender_id" not in data and  "receiver_id" not in data and "message" not in data:
        return make_response({"msg": "error"}, 401)
    message = messages.post_messages(data)
    return make_response(jsonify(message), 200)


@app.route('/api/pos/messages/<id>', methods=['PUT'])
def put_message(id: str):
    data = dict(request.json)
    messages.put_message(id, data)
    return make_response(jsonify({"id": id}), 200)


@app.route('/api/pos/messages/<id>', methods=['DELETE'])
def delete_message(id):
    messages.delete_message(id)
    return make_response("", 200)


if __name__ == '__main__':
    app.run(port=5000, debug=True)
