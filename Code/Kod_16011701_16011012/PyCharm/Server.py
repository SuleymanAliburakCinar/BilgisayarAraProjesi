from flask import Flask, request, jsonify
import util

app = Flask(__name__)


@app.route('/get_location_names', methods=['GET'])
def get_location_names():
    response = jsonify({
        'locations': util.get_location_names()
    })
    response.headers.add('Access-Control-Allow-Origin', '*')

    return response


@app.route('/predict_home_price', methods=['POST', 'GET'])
def predict_home_price():

    data = request.get_json()

    location = data["Locations"]
    room = data["Rooms"]
    distance = data["Distance"]
    bathroom = data["Bathroom"]
    car = data["Car"]
    total = data["Total"]
    type = data["Type"]

    response = jsonify(
         util.get_estimated_price(location, room, distance, bathroom, car, total, type)
    )
    response.headers.add('Access-Control-Allow-Origin', '*')

    return response


if __name__ == "__main__":
    print("Starting Python Flask Server For Home Price Prediction...")
    util.load_saved_artifacts()
    app.run()
