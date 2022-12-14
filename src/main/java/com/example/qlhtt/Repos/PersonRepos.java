package com.example.qlhtt.Repos;

import com.example.qlhtt.Entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepos  {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;





    public List<Person> getall() {
        //jdbcTemplate.setDataSource(dataSource);
        //dataSource.toString();
        //jdbcTemplate.getDataSource().toString();
        try {
            List<Person> persons = jdbcTemplate.query("Select * From Person", BeanPropertyRowMapper.newInstance(Person.class));
            return persons;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Person getbyid(int id){
        try{
            Person person= jdbcTemplate.queryForObject("Select * From Person Where id=" + Integer.toString(id), (rs, rowNum) ->
                    new Person(rs.getInt("id"), rs.getString("name"), rs.getString("gender"),rs.getString("identity_card"), rs.getString("day_of_birth"), rs.getString("phone_num"), rs.getString("address")));
            return person;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public Person getbyidcard(String idcard){
        try{
            Person person= jdbcTemplate.queryForObject("Select * From Person Where identity_card='" + idcard+"'", (rs, rowNum) ->
                    new Person(rs.getInt("id"), rs.getString("name"), rs.getString("gender"),rs.getString("identity_card"), rs.getString("day_of_birth"), rs.getString("phone_num"), rs.getString("address")));
            return person;
        }
        catch(Exception e){
            return null;
        }

    }

    public boolean insertPerson(Person person){
        try{
            System.out.print(person.getName()+" "+person.getGender()+" "+person.getIdentity_card()+" "+person.getDay_of_birth()+" "+person.getPhone_num()+" "+person.getAddress());
            jdbcTemplate.update("INSERT INTO PERSON(name, gender,identity_card, day_of_birth, phone_num, address) VALUES(?, ?, ?, ?, ?, ?)",
                    person.getName(), person.getGender(), person.getIdentity_card(), person.getDay_of_birth(), person.getPhone_num(), person.getAddress());
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public List<Person> getCustomer(){
        try{
            List<Integer> id=jdbcTemplate.query("Select customer_id from customeruser where status=1", (rs, rowNum) -> rs.getInt("customer_id")  );
            List<Person> personList=new ArrayList<>();
            System.out.print("xxxxxxxxxxxxxxx "+id.size()+" xxxxxxxxxxxxxxxxxxxxxxxx");
            for(int i=0;i<id.size();i++){
                System.out.print("xxxxxxx"+id.get(i)+"xxxxxxxx");
                Person temp=getbyid(id.get(i));
                personList.add(temp);
            }
            return personList;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Person> getStaff(){
        try{
            List<Integer> id=jdbcTemplate.query("Select staff_id from staff where status=1", (rs, rowNum) -> rs.getInt("staff_id")  );
            List<Person> personList=new ArrayList<>();
            System.out.print("xxxxxxxxxxxxxxx "+id.size()+" xxxxxxxxxxxxxxxxxxxxxxxx");
            for(int i=0;i<id.size();i++){
                System.out.print("xxxxxxx"+id.get(i)+"xxxxxxxx");
                Person temp=getbyid(id.get(i));
                personList.add(temp);
            }
            return personList;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Person person){
        try{
            jdbcTemplate.update("Update Person set name=?, gender=?, identity_card=?, day_of_birth=?, phone_num=?, address=? where id=? ",person.getName(),person.getGender(),person.getIdentity_card(),person.getDay_of_birth()
            ,person.getPhone_num(),person.getAddress(),person.getId());
            System.out.print("xxxxxxxxxx Update done XXXXXXXXXXX");
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean delete(int id){
        try{
            jdbcTemplate.update("Delete Person where id=? ",id);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    public String checkUser(Person person, String status){
        if(person.getName().equals(""))
            return "Vui l??ng nh???p h??? v?? t??n";
        else if(person.getIdentity_card().equals(""))
            return "CMND/CCCD kh??ng ???????c b??? tr???ng!";
        else if(person.getIdentity_card().length()!=9 && person.getIdentity_card().length()!=12)
            return "CCCD/CMND kh??ng h???p l???!";
        else if(this.getbyidcard(person.getIdentity_card())!=null){
            Person p = this.getbyidcard(person.getIdentity_card());
            if(status.equals("update")) {
                if (p.getId() != person.getId()) {
                    return "CMND/CCCD ???? t???n t???i";
                } else
                    return "";
            }
            return "CMND/CCCD ???? t???n t???i";
        }
        else if(person.getDay_of_birth().equals(""))
            return "Vui l??ng ch???n ng??y sinh";

        else if(person.getPhone_num().length()!=10)
            return "S??? ??i???n tho???i kh??ng h???p l???!";
        return "";
    }
}