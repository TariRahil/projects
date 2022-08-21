package dl;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bl.common.DateFormat;
import bl.common.StringFormat;
import bo.Resource;
import bo.views.ResourceView;
import init.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


public class ResourceDL {

    public List<Resource> selectAll1(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String hQl = "from Resource ";
        Query query = session.createQuery(hQl);
        List<Resource> resource =  query.list();

        session.getTransaction().commit();
        session.close();

        return resource;
    }
    public List<ResourceView> selectAll(int currentPage, int recordPerPage){
        currentPage-=1;//show first record from 1 not zero

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        //( (Integer) session.createQuery("select count(*) from Resource ").iterate().next() ).intValue();
        String hQl = "select Count(*) from Resource ";
        Query query1 = session.createQuery(hQl);
        long count = (long)query1.uniqueResult();
        //int count = ( (Integer) session.createQuery("select count(*) from Resource ").iterate().next() ).intValue();
        int pageCount= (count%recordPerPage)>0? ((int)(count/recordPerPage)+1) :(int) (count/recordPerPage);

        String hql2 = " from Resource ";
        Query query2 = session.createQuery(hql2).setFirstResult(currentPage*recordPerPage).setMaxResults(recordPerPage);

        List<Resource> resourceList = query2.list();
        List<ResourceView> resourceViewList = new ArrayList<ResourceView>();
        for (Resource resource:resourceList) {
            ResourceView resourceView= new ResourceView();
            resourceView.setName(resource.getName());
            resourceView.setLocation(resource.getLocation());
            resourceView.setActive(resource.getActive());
            resourceView.setFloor(resource.getFloor());
            resourceView.setOwner(resource.getOwner());
            resourceView.setResourceId(resource.getResourceId());
            resourceView.setUnitId(resource.getUnitId());
            resourceView.setPageCount(pageCount);
            resourceView.setCount((int) count);
            resourceViewList.add(resourceView);
        }

        session.getTransaction().commit();
        session.close();

        return resourceViewList;
    }

    public Resource selectByResourceId(Integer resourceId)
    {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Resource resource = session.get(Resource.class,resourceId);
        session.getTransaction().commit();
            session.close();

        return resource;
    }

    public String insertOrUpdateResource(Integer resourceId, String resourceName, Integer resourceOwner, String resourceCurrentPlace,
                                         Byte floor, Integer unitId, Integer by, Boolean status) {
        try {
            System.out.println("HI MY DEAR RAHIL...RESOURCEDL");
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            Resource resource = new Resource();

            resource.setName(StringFormat.AjaxDecoder(resourceName));
            resource.setOwner(resourceOwner);
            resource.setLocation(StringFormat.AjaxDecoder(resourceCurrentPlace));
            resource.setFloor(floor);
            resource.setUnitId(unitId);
            resource.setActive(status);

            if (resourceId==0){

                resource.setCreatedBy(by);
                resource.setCreationDate(DateFormat.getCurrentDate());
                session.save(resource);
            }else{

                resource.setResourceId(resourceId);
                resource.setModifiedBy(by);
                resource.setModifiedDate(DateFormat.getCurrentDate());
                session.update(resource);
            }

            session.getTransaction().commit();
            session.close();

            return "operation successfully.";

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
}
