import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from '../api/axios.jsx';

export default function EnterPage() {
  const [invitationCode, setInvitationCode] = useState();
  const [isHover, setIsHover] = useState(false);
  const navigate = useNavigate();
  const handleClick = () => {
    axios
      .post('/family/join', { invitationCode: invitationCode })
      .then((response) => {
        console.log(response.data);
        navigate('/welcome');
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const handleInput = (e) => {
    setInvitationCode(e.target.value);
  };

  const enterMouse = () => {
    setIsHover(true);
  };

  const outMouse = () => {
    setIsHover(false);
  };

  return (
    <>
      <div className="h-screen text-center align-middle w-full py-16 px-48 z-20">
        <div className="border-8 border-black bg-white h-full rounded-xl p-10">
          <h1 className="text-2xl mb-20">참여하기</h1>
          <div className="flex align-middle h-1/2 flex-col pt-10 ">
            <p className=" text-xl">가족 코드 입력</p>
            <div className="border-4 border-black rounded-md w-3/4 m-auto">
              <input type="text" onChange={handleInput} className="px-4 py-2 w-5/6 ml-4" style={{ outline: 'none' }} />
              <button onClick={handleClick} className="bg-gray-300 px-4 py-1 w-20">
                참여
              </button>
            </div>
            <span
              onMouseEnter={enterMouse}
              onMouseLeave={outMouse}
              className=" w-2/6 mx-auto transition-all duration-50"
              style={{ color: isHover ? '#4ADE80' : 'black' }}
            >
              {isHover ? (
                <Link to="/createtree/family">
                  <span className="text-2xl">새로운 가족 만들기</span>
                </Link>
              ) : (
                <span className="text-2xl">아직 가족코드가 없다면?</span>
              )}
            </span>
          </div>
        </div>
      </div>
    </>
  );
}
