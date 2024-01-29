import { useNavigate } from 'react-router-dom';
import back from '../../assets/images/back.png';

export default function BackButton() {
  const navigate = useNavigate();
  const goBackHandler = () => {
    navigate(-1);
  };
  return (
    <div className="flex justify-end">
      <button onClick={goBackHandler} className="h-10 w-10">
        <img src={back} alt="back" />
      </button>
    </div>
  );
}
