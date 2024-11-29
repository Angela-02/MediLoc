from flask import *
from database import *



public=Blueprint("public",__name__)


@public.route('/')
def home_page():

    return render_template('home.html')


@public.route('/login',methods=['get','post'])
def login():

    if 'login' in request.form:
        uname=request.form['uname']
        password=request.form['pwd']

        qry="select * from login where user_name='%s' and password ='%s'"%(uname,password)
        res=select(qry)

        if res:
            session['login_id']=res[0]['login_id']

            if res[0]['user_type']=='admin':
                return ("<script>alert('login successfull');window.location='/admin_home'</script>")
            
            if res[0]['user_type']=='hospital':
                qrt="select * from hospital where login_id='%s'"%(session['login_id'])
                res1=select(qrt)

                if res1:
                    session['hospital_id']=res1[0]['hospital_id']

                    print(session['hospital_id'],"_________________")

                return ("<script>alert('login successfull');window.location='/hospital_home'</script>")
            
            if res[0]['user_type']=='doctor':

                qrt="select * from doctors where login_id='%s'"%(session['login_id'])
                res2=select(qrt)

                if res2:
                    session['doctors_id']=res2[0]['doctors_id']

                return ("<script>alert('login successfull');window.location='/doctor_home'</script>")
        else:
            return ("<script>alert('Invalid Username or Password ');window.location='/login'</script>")


    return render_template('login.html')



@public.route('/hospital_registration',methods=['get','post'])
def hospital_registration():

    if 'add' in request.form:

        hospital_name=request.form['hname']
        place=request.form['place']
        phone=request.form['phone']
        email=request.form['mail']
        latitude=request.form['latitude']
        longitude=request.form['longitude']
        username=request.form['uname']
        password=request.form['pwd']


        qry="insert into login values(null,'%s','%s','pending')"%(username,password)
        res=insert(qry)


        qry1="insert into hospital values(null,'%s','%s','%s','%s','%s','%s','%s')"%(res,hospital_name,place,phone,email,latitude,longitude)
        res1=insert(qry1)

        return ("<script>alert('Register  successfull');window.location='/login'</script>")




    return render_template("hospital_register.html")