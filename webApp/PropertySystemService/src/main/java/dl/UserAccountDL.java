package dl;

import bl.common.DateFormat;
import bl.common.StringFormat;
import bo.UserAccount;
import init.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.util.List;

public class UserAccountDL {
    public UserAccount selectByUserId(String userId)
    {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hQl="from UserAccount where userId=: id ";
        Query query = session.createQuery(hQl);
        query.setParameter("id" ,Integer.parseInt(userId) );
        UserAccount user = (UserAccount) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public UserAccount selectByUsernamePassword(String userName, String password)
    {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hQl="from UserAccount where userName=: userName and password =: password";
        Query query = session.createQuery(hQl);
        query.setParameter("userName" , userName);
        query.setParameter("password" , password);
        UserAccount userAccount = (UserAccount) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return userAccount;
    }
    public List<UserAccount> selectByWord(String word)
    {
        word=StringFormat.AjaxDecoder(word);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hQl="from UserAccount where firstName Like :firstName or lastName  Like :lastName";
        Query query = session.createQuery(hQl);
        query.setParameter("firstName" , "%"+word+"%");
        query.setParameter("lastName" , "%"+word+"%");
        List<UserAccount> users =  query.list();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    public String insertUser(String firstName,String lastName,String userName,String password,Integer managerId,Integer unitId,boolean active,Integer createdBy )
    {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            UserAccount userAccount = new UserAccount();
            userAccount.setFirstName(StringFormat.AjaxDecoder(firstName));
            userAccount.setLastName(StringFormat.AjaxDecoder(lastName));
            userAccount.setUserName(userName);
            userAccount.setPassword(password);
            userAccount.setUnitId(unitId);
            userAccount.setActive(active);
            userAccount.setManagerId(managerId);
            userAccount.setActive(true);
            userAccount.setCreatedBy(createdBy);
            userAccount.setCreationDate(DateFormat.getCurrentDate());
            session.save(userAccount);
            session.getTransaction().commit();
            session.close();
            return "add successfully.";
        }
        catch (Exception ex)
        {
            return  ex.getMessage();
        }
    }
    public String selectbyUserName(String userName )
    {
        String msg="non-duplicate";
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            String hQl="from UserAccount where userName = :username ";
            Query query = session.createQuery(hQl);
            query.setParameter("username" , userName);
            UserAccount user = (UserAccount) query.uniqueResult();
            session.getTransaction().commit();
            session.close();
            if(user!=null)
            {
                msg= "duplicate";
            }
            return msg;
        }
        catch (Exception ex)
        {
            return  ex.getMessage();
        }
    }

}
