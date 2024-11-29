from flask import *
from database import *

api=Blueprint("api",__name__)


@api.route("/user_login")
def user_login():
    data={}
    username=request.args['username']
    password=request.args['password']

    qry="select * from login where user_name='%s' and password='%s'"%(username,password)
    res=select(qry)

    if res:
        data["status"]="success"
        data['data']=res
    else:
        data["status"]="failed"

    return str(data)

@api.route("/user_register")
def user_register():
    data={}
    fname=request.args['fname']
    lname=request.args['lname']
    gender=request.args['gender']
    phone=request.args['phone']
    email=request.args['email']
    place=request.args['place']
    age=request.args['age']
    username=request.args['username']
    password=request.args['password']
    longitude=request.args['longitude']
    latitude=request.args['latitude']

    qry="insert into login values(null,'%s','%s','user')"%(username,password)
    res=insert(qry)

    qry1="insert into user values(null,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(res,fname,lname,place,phone,email,age,gender,latitude,longitude)
    res1=insert(qry1)

    print(res1,"_______________")

    if res1:
        data['status']='success' 
    else:
        data['status']='failed'

    return str(data)


@api.route('/user_send_enquiry')
def user_send_enquiry():
    data={}
    enquiry=request.args['enquiry']
    userid=request.args['userid']
    hospitalid=request.args['hospitalid']

    qrt="insert into enquiry values(null,(select user_id from user where login_id='%s'),'%s','%s','pending',curdate())"%(userid,hospitalid,enquiry)
    res=insert(qrt)

    if res:
        data['status']='success'
    else:
        data['status']='failed'
    data['method']='send'

    return str(data)


@api.route('/User_view_enquiry')
def User_view_enquiry():
    data={}
    user_id=request.args['user_id']

    qry="select * from  enquiry where user_id=(select user_id from user where login_id='%s')"%(user_id)
    res=select(qry)

    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    data['method']='view'

    return str(data)



@api.route('/user_send_complaints')
def user_send_complaints():
    data={}

    complaints=request.args['complaints']
    userid=request.args['userid']

    qry="insert into complaint values(null,'%s','%s','pending',curdate())"%(userid,complaints)
    res=insert(qry)

    if res:
        data['status']='success'
    else:
        data['status']='failed'
    data['method']='send'

    return str(data)


@api.route('/User_view_complaints')
def User_view_complaints():
    data={}
    user_id=request.args['user_id']

    qry="select * from complaint where sender_id='%s'"%(user_id)
    res=select(qry)

    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    data['method']='view'
    return str(data)



@api.route('/User_view_hospitals')
def User_view_hospitals():
    data={}

    # qry="SELECT * FROM hospital INNER JOIN `department` USING(hospital_id) GROUP BY hospital_id"

    lati=request.args['lati']
    logi=request.args['logi']

    print(lati,"_________________")

    print(logi,"___________________")

    q=" SELECT *, (3959 * ACOS(COS(RADIANS(%s)) * COS(RADIANS(latitude)) * COS(RADIANS(longitude) - RADIANS(%s)) + SIN(RADIANS(%s)) * SIN(RADIANS(latitude)))) AS user_distance FROM hospital HAVING user_distance < 11.068 ORDER BY user_distance" % (lati,logi,lati)
    res=select(q)
    data['view']=res

    print(res,"______________________________-")
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    data['method']='view'

    return str(data)


@api.route('/searchhospital')
def searchhospital():
    data={}
    search=request.args['search']+'%'

    qry="SELECT * FROM hospital INNER JOIN `department` USING(hospital_id) where hospital_name like '%s' or department_name like '%s' or place like '%s' "%(search,search,search)
    res=select(qry)

    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    data['method']='view'

    return str(data)

@api.route('/User_view_hospital_departments')
def User_view_hospital_departments():
    data={}

    hospital_id=request.args['hospital_id']

    qry="select * from department where hospital_id='%s'"%(hospital_id)
    res=select(qry)

    if res:
        
        data['status']='success'
        data['data']=res

    else:
        data['status']='failed'

    return str(data)



@api.route('/User_view_doctors')
def User_view_doctors():
    from datetime import datetime
    current_date = datetime.now()
    formatted_date = current_date.strftime("%Y-%m-%d")
    print(formatted_date,"+++++++++++++++++++++++++++++")

    data={}
    departments_id=request.args['departments_id']

    qry="select * from doctors inner join schedule using(doctors_id) where department_id='%s' and date='%s' "%(departments_id,formatted_date)
    res=select(qry)

    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    data['method']='view'


    return str(data)



@api.route('/user_make_appointment')
def user_make_appointment():
    data={}
    description=request.args['desc']
    userid=request.args['userid']
    doctors_id=request.args['doctors_id']

    qry="insert into appointment values(null,'%s',(select user_id from user where login_id='%s'),'%s',curdate(),'pending')"%(doctors_id,userid,description)
    res=insert(qry)

    if res:
        data['status']='success'
    else:
        data['status']='failed'

    return str(data)

@api.route('/User_view_appointments')
def User_view_appointments():
    data={}
    user_id=request.args['user_id']

    qry="select * from appointment where user_id=(select user_id from user where login_id='%s')"%(user_id)
    res=select(qry)
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    data['method']='viewappointment'
    return str(data)


@api.route('/makepayment')
def makepayment():
    data={}

    amout=request.args['amout']
    userid=request.args['userid']
    appointmentid=request.args['appointmentid']

    qry="update appointment set status='paid' where appointment_id='%s'"%(appointmentid)
    update(qry)

    qry="insert into payment values(null,'%s',(select user_id from user where login_id='%s'),'%s','paid',curdate())"%(appointmentid,userid,amout)
    res=insert(qry)

    if res:
        data['status']='success'
    else:
        data['status']='failed'

    return str(data)

@api.route('/viewprescription')
def viewprescription():
    data={}

    appid=request.args['appid']

    qry="select * from prescription where appointment_id='%s'"%(appid)
    res=select(qry)

    if res:
        data['status']='success'
        data['data']=res

    else:
        data['status']='failed'
    data['method']='viewprescription'

    return str(data)


@api.route('/User_add_review')
def User_add_review():
    data={}

    prescription_id=request.args['prescription_id']
    user_id=request.args['user_id']
    review=request.args['review']

    qry="select * from prescription where prescription_id='%s'"%(prescription_id)
    rr=select(qry)
    apid=rr[0]['appointment_id']

    qry1="select * from appointment where appointment_id='%s'"%(apid)
    rr1=select(qry1)
    did=rr1[0]['doctors_id']

    qry2="insert into review values(null,(select user_id from user where login_id='%s'),'%s','%s',curdate())"%(user_id,did,review)
    res=insert(qry2)

    if res:
        data['status']='success'
    else:
        data['status']='failed'

    return str(data)