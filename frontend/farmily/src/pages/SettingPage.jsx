import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { CopyToClipboard } from 'react-copy-to-clipboard';
import axios from '../api/axios.jsx';

export default function SettingPage() {
  const family = useSelector((state) => state.family.value);
  const [familyName, setFamilyName] = useState(family.name);
  const [motto, setMotto] = useState(family.motto);
  const [invitationCode, setInvitationCode] = useState(family.invitationCode);
  const [familyMembers, setFamilyMembers] = useState([
    {
      id: 0,
      nickname: '',
      role: '',
      me: false
    }
  ])

  useEffect(() => {
    axios
    .get(`family/${family.id}/familyMembers`)
    .then((response) => {
      setFamilyMembers(response.data)
    })
    .catch((error) => {
      console.log(error)
    })
  }, []);
  const handleNameChange = (e) => {
    setFamilyName(e.target.value);
  };
  const handleMottoChange = (e) => {
    setMotto(e.target.value);
  };
  return (
    <>
      <h1>설정 페이지</h1>
      <Link to="/createtree">가족 생성하기</Link>
      <div className="w-full h-5/6 m-auto pt-10">
      <div className="w-full flex justify-around mb-10 h-12">
          <div className="rounded-md px-4 w-full pl-4 flex justify-center items-center">
              {familyMembers.map((member) => (
                <div className=" bg-gray-300 rounded-3xl px-2 mx-2">{member.role === 'LEADER'? '👑 ':''}{member.nickname}</div>
              ))}
          </div>
        </div>

        <div className="w-full flex justify-around items-center mb-10 h-12">
          <p className="w-1/4">가족 이름</p>
          <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between">
            <input
              value={familyName}
              type="text"
              onChange={handleNameChange}
              className="w-5/6"
            />
            <button className="bg-gray-300 px-4 w-20">저장</button>
          </div>
        </div>

        <div className="w-full flex justify-around items-center mb-10 h-12">
          <p className="w-1/4">가훈</p>
          <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between">
            <input
              type="text"
              value={motto}
              onChange={handleMottoChange}
              className="w-5/6"
            />
            <button className="bg-gray-300 px-4 w-20">저장</button>
          </div>
        </div>

        <div className="w-full flex justify-around items-center mb-10 h-12">
          <p className="w-1/4">초대코드</p>
          <div className="border-4 border-black rounded-md p-1 w-1/2 flex justify-between pl-4">
            <p className="w-5/6 truncate text-left">{invitationCode}</p>
            <CopyToClipboard text={invitationCode}>
              <button className="bg-gray-300 px-4 w-20">복사</button>
            </CopyToClipboard>
          </div>
        </div>
      </div>
    </>
  );
}
