from flask import *

from database import *


doctor=Blueprint("doctor",__name__)


@doctor.route('/doctor_home')
def doctor_home():

    return render_template('doctor_home.html')

@doctor.route('/doctor_view_appointment')
def doctor_view_appointment():
    data={}

    qry="select * from appointment inner join user using(user_id) where doctors_id='%s'"%(session['doctors_id'])
    res=select(qry)
    data['view']=res


    return render_template('doctor_view_appointment.html',data=data)

@doctor.route('/doctor_view_payment')
def doctor_view_payment():
    id=request.args['id']

    data={}

    qry="select * from payment where  appointment_id='%s'"%(id)
    res=select(qry)
    data['view']=res

    return render_template("doctor_view_payment.html",data=data)

@doctor.route('/doctor_add_prescription',methods=['get','post'])
def doctor_add_prescription():
    if 'add' in request.form:
        id=request.args['id']

        medicinedet=request.form['md']

        qry="insert into prescription values(null,'%s','%s',curdate())"%(id,medicinedet)
        insert(qry)

        qr1="update appointment set status='prescription Added' where appointment_id='%s'"%(id)
        update(qr1)

        return ("<script>alert('Add successfull');window.location='/doctor_view_appointment'</script>")


    return render_template("doctor_add_prescription.html")