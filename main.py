from flask import *
from public import public
from admin import admin
from hospital import hospital
from doctor import doctor
from api import api

app=Flask(__name__)

app.secret_key="abcdet"

app.register_blueprint(public)
app.register_blueprint(admin)
app.register_blueprint(hospital)
app.register_blueprint(doctor)
app.register_blueprint(api)


app.run(debug=True,port=5006,host="0.0.0.0")



