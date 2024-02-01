import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../api/axios.jsx';

export default function EnterPage() {
  const [invitationCode, setInvitationCode] = useState();
  const navigate = useNavigate();
  const handleClick = () => {
    axios
      .post('/family/join', { invitationCode: invitationCode })
      .then((response) => {
        console.log(response.data);
        navigate('/tree');
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const handleInput = (e) => {
    setInvitationCode(e.target.value);
  };
  return (
    <>
      <div className="h-screen text-center align-middle w-full py-24 px-60 ">
        <div className="border-8 border-black bg-white h-full rounded-xl p-10">
          <h1 className="text-2xl mb-20">참여하기</h1>
          <div className="flex align-middle h-1/2 flex-col pt-10 ">
            <p className=" text-xl">가족 코드 입력</p>
            <div className="border-4 border-black rounded-md w-3/4 m-auto">
              <input
                type="text"
                onChange={handleInput}
                className="px-4 py-2 w-5/6 ml-4"
                style={{ outline: 'none' }}
              />
              <button
                onClick={handleClick}
                className="bg-gray-200 px-6 py-1 border-2 border-black rounded-sm"
              >
                참여
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
