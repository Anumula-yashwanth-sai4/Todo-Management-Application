import { useState,useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate,useParams } from "react-router-dom";
import axios from "axios";

export function Form(){
  const [task,setTask]=useState({title:"",description:"",completed:false});
  const [error,setError]=useState("");
  const [loadError,setLoadError]=useState(false);
  const navigate=useNavigate();
  const {id}=useParams();
//fetch edit details
  useEffect(()=>{
    if(!id)return;
    axios.get("http://localhost:8081/todo/showTasks")
      .then(res=>{
        const found=res.data.find(t=>t.id==id);
        found
          ? (setTask(found),setError(""),setLoadError(false))
          : (setError("Task not found"),setLoadError(true));
      })
      .catch(err=>{
        console.log(err);
        setError("Failed to show the list");
        setLoadError(true);
      });
  },[id]);

  const handleChange=e=>{
    const {name,value,type,checked}=e.target;
    setTask({...task,[name]:type==="checkbox"?checked:value});
  };
//add and edit submit
  const handleSubmit=e=>{
    e.preventDefault();
    (id
      ? axios.patch(`http://localhost:8081/todo/editTask/${id}`,task)
      : axios.post("http://localhost:8081/todo/add",task)
    )
      .then(()=>{
        setError("");
        navigate("/lists");
      })
      .catch(err=>{
        console.log(err);
        setError("Failed to add details");
      });
  };

  return(
    <div style={{backgroundColor:"#F3E5F5",minHeight:"93vh",paddingTop:"80px"}}>
      <div className="container-fluid">
        <h2 className="text-center mb-4">{id?"Edit Task":"Add Task"}</h2>
        <form onSubmit={handleSubmit} className="w-50 mx-auto">
          <div className="card p-4 shadow">
            {error&&<div className="alert alert-danger text-center">{error}</div>}
            {(!id||!loadError)&&(
              <>
                <div className="mb-3">
                  <label className="form-label">Title</label>
                  <input className="form-control" type="text" name="title" value={task.title} onChange={handleChange} required/>
                </div>
                <div className="mb-3">
                  <label className="form-label">Description</label>
                  <input className="form-control" type="text" name="description" value={task.description} onChange={handleChange}/>
                </div>
                {id&&(
                  <div className="form-check mb-3">
                    <input className="form-check-input" type="checkbox" name="completed" checked={task.completed} onChange={handleChange}/>
                    <label className="form-check-label">Completed</label>
                  </div>
                )}
                <div className="mt-3 text-center">
                  <button type="submit" className="btn btn-info me-3">Submit</button>
                  {id&&<button type="button" className="btn btn-secondary" onClick={()=>navigate("/lists")}>Back to List</button>}
                </div>
              </>
            )}
          </div>
        </form>
      </div>
    </div>
  );
}