from flask import *
from database import *


hospital=Blueprint("hospital",__name__)


@hospital.route('/hospital_home',methods=['get','post'])
def hospital_home():
    return render_template("hospital_home.html")


@hospital.route('/manage_departments',methods=['get','post'])
def manage_departments():
    data={}

    qry2="select * from department where hospital_id='%s'"%(session['hospital_id'])
    res=select(qry2)
    data['view']=res

    if 'add' in request.form:

        department_name=request.form['dname']

        qry1="insert into department values(null,'%s','%s')"%(session['hospital_id'],department_name)
        insert(qry1)

        return ("<script>alert('Add  successfull');window.location='/manage_departments'</script>")
    
    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']

    else:
        action=None

    if action=='update':
        qry3="select * from department where department_id='%s'"%(id)
        ress=select(qry3)
        data['up']=ress

    if 'update' in request.form:
        department_name=request.form['dname']

        qry5="update department set department_name='%s' where department_id='%s'"%(department_name,id)
        update(qry5)
        return ("<script>alert('Update  successfull');window.location='/manage_departments'</script>")

    if action=='delete':
        qry4="delete from department where department_id='%s'"%(id)
        delete(qry4)

        return ("<script>alert('Delete Successfully');window.location='/manage_departments'</script>")

        
    return render_template("hospital_manage_departments.html",data=data)


@hospital.route('/hospital_manage_doctors', methods=['get', 'post'])
def hospital_manage_doctors():
    data = {}  # Ensure data is initialized at the start

    id = request.args.get('id')  # Using .get() to handle missing 'id' gracefully

    if 'action' in request.args:
        action = request.args.get('action')
    else:
        action = None

    if action == 'delete':
        qrt1 = "delete from doctors where login_id='%s'" % id
        delete(qrt1)
        return "<script>alert('Delete successful');window.location='/manage_departments'</script>"

    if action == 'update':
        qup = "select * from doctors where login_id='%s'" % id
        resss = select(qup)
        data['up'] = resss

    if 'up' in request.form:
        dep = request.form['dep']
        first_name = request.form['fname']
        last_name = request.form['lname']
        place = request.form['place']
        phone = request.form['phone']
        email = request.form['mail']
        qualification = request.form['qual']

        qryup = "update doctors set first_name='%s', last_name='%s', place='%s', phone='%s', email='%s', qualification='%s' where login_id='%s'" % (
            first_name, last_name, place, phone, email, qualification, id)
        update(qryup)

        return "<script>alert('Update successful');window.location='/manage_departments'</script>"

    # Fetch department list
    qry = "select * from department where hospital_id='%s'" % session['hospital_id']
    res1 = select(qry)
    data['department'] = res1

    # Fetch doctors based on department ID (you may want to adjust the logic depending on your use case)
    qrt = "select * from doctors where department_id='%s'" % id
    ress = select(qrt)
    data['doctors'] = ress

    if 'add' in request.form:
       
        first_name = request.form['fname']
        last_name = request.form['lname']
        place = request.form['place']
        phone = request.form['phone']
        email = request.form['mail']
        qualification = request.form['qual']
        username = request.form['uname']
        password = request.form['pwd']

        # Inserting new doctor into login and doctors tables
        qry = "insert into login values(null, '%s', '%s', 'doctor')" % (username, password)
        res = insert(qry)

        qry1 = "insert into doctors values(null, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')" % (
            res, id, session['hospital_id'], first_name, last_name, place, phone, email, qualification)
        insert(qry1)

        return "<script>alert('Add successful');window.location='/manage_departments'</script>"

    return render_template("hospital_manage_doctors.html", data=data)



@hospital.route('/hospital_add_doctors_schedule',methods=['get','post'])
def hospital_add_doctors_schedule():
    id=request.args['id']

    if 'add' in request.form:
        date=request.form['date']
        time=request.form['time']
        etime=request.form['etime']
        status=request.form['status']


        qry="insert into schedule values(null,'%s','%s','%s','%s','%s')"%(id,date,time,etime,status)
        insert(qry)

        return ("<script>alert('Add successfull');window.location='/manage_departments'</script>")

    

    return render_template("hospital_add_doctors_schedule.html")



@hospital.route('/hospital_view_patients')
def hospital_view_patients():
    data={}

    qry="SELECT * FROM doctors INNER JOIN appointment USING(doctors_id) INNER JOIN user USING(user_id) WHERE hospital_id='%s'"%(session['hospital_id'])
    res=select(qry)
    data['view']=res

    return render_template("hospital_view_patients.html",data=data)

@hospital.route('/hospital_view_enquiry')
def hospital_view_enquiry():
    data={}

    qry="select * from enquiry where hospital_id='%s'"%(session['hospital_id'])
    res=select(qry)
    data['enquiry']=res


    return render_template("hospital_view_enquiry.html",data=data)


@hospital.route('/hospital_send_reply',methods=['get','post'])
def hospital_send_reply():
    id=request.args['id']

    if 'send'  in request.form:
        reply=request.form['reply']
        
        qry="update  enquiry set reply='%s' where enquiry_id='%s' "%(reply,id)
        update(qry)
        return ("<script>alert('Reply sent successfully');window.location='/hospital_view_enquiry'</script>")


    return render_template("hospital_send_reply.html")




@hospital.route('/hospital_view_reviews')
def hospital_view_reviews():
    data={}

    qry="SELECT * FROM doctors INNER JOIN review USING(doctors_id) WHERE hospital_id='%s'"%(session['hospital_id'])
    res=select(qry)
    data['view']=res

    return render_template("hospital_view_reviews.html",data=data)