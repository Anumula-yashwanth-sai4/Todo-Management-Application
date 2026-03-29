import axios from "axios";
import { useEffect,useState } from "react";
import { useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

export function TodoList(){
  const [tasks,setTasks]=useState([]);
  const [selectedTask,setSelectedTask]=useState(null);
  const [error,setError]=useState("");
  const navigate=useNavigate();
 //list of tasks
  useEffect(()=>{
    axios.get("http://localhost:8081/todo/showTasks").then(res=>{ setTasks(res.data);setError("");})
      .catch(err=>{
        console.log(err);
        setError("Failed to load tasks");
      });
  },[]);

  const handleDelete=id=>{
    if(window.confirm("Delete task?")){
      axios.delete(`http://localhost:8081/todo/delete/${id}`).then(()=>setTasks(tasks.filter(t=>t.id!==id)))
        .catch(err=>{
          console.log(err);
          setError("Failed to delete task");
        });
    }
  };
//status
  const handleToggle=task=>{
    axios.patch(`http://localhost:8081/todo/statuscompleted/${task.id}`,{
      ...task,completed:!task.completed
    })
      .then(()=>setTasks(
        tasks.map(t=>t.id===task.id?{...t,completed:!t.completed}:t)
      ))
      .catch(err=>{
        console.log(err);
        setError("Failed to update status");
      });
  };

  const handleEdit=id=>navigate(`/edit/${id}`);

  return(
    <>
      <div className="container-fluid mt-3">
        <h2 className="text-center mb-4">List of Tasks</h2>

        {error&&<div className="alert alert-danger text-center w-75 mx-auto">{error}</div>}

        <table className="table table-striped w-75 mx-auto">
          <thead>
            <tr>
              <th>Title</th>
              <th>Created At</th>
              <th>Status</th>
              <th>View</th>
              <th>Edit</th>
              <th>Delete</th>
            </tr>
          </thead>
          <tbody>
            {tasks.map(task=>(
              <tr key={task.id}>
                <td>{task.title}</td>
                <td>{task.createdAt?new Date(task.createdAt).toLocaleString():"N/A"}</td>
                <td className="align-middle">
        
                    <input type="checkbox" checked={task.completed} onChange={()=>handleToggle(task)} /> 
                    <span className="ms-2">
                        {task.completed?"Done":"Pending"}
                    </span>
                     
                    
                </td>
                <td>
                  <button className="btn btn-info" onClick={()=>setSelectedTask(task)}>
                    View
                  </button>
                </td>
                <td>
                  <button className="btn btn-secondary" onClick={()=>handleEdit(task.id)}>
                    Edit
                  </button>
                </td>
                <td>
                  <button className="btn btn-danger" onClick={()=>handleDelete(task.id)}>
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {selectedTask&&(
        <div style={{
          position:"fixed",top:0,left:0,width:"100%",height:"100%",
          backgroundColor:"#F3E5F5",display:"flex",
          justifyContent:"center",alignItems:"center",zIndex:1000
        }}>
          <div className="card p-4" style={{width:"25rem"}}>
            <h4 className="text-center mb-3">Task Details</h4>
            {error && ( <div className="alert alert-danger text-center"> {error} </div>)}
            <p><strong>ID:</strong> {selectedTask.id}</p>
            <p><strong>Title:</strong> {selectedTask.title}</p>
            <p><strong>Description:</strong> {selectedTask.description}</p>
            <p><strong>Status:</strong> {selectedTask.completed?"Done":"Pending"}</p>
            <p><strong>Created At:</strong> {selectedTask.createdAt?new Date(selectedTask.createdAt).toLocaleString():"N/A"}</p>
            <button className="btn btn-dark mt-3" onClick={()=>{setSelectedTask(null); setError("")}}>
              Close
            </button>
          </div>
        </div>
      )}
    </>
  );
}