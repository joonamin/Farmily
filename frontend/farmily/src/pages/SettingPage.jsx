import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import { CopyToClipboard } from 'react-copy-to-clipboard';
import axios from '../api/axios.jsx';

export default function SettingPage() {
  const navigate = useNavigate();
  const family = useSelector((state) => state.family.value);
  const user = useSelector((state) => state.user.value);
  const [isChanged, setIsChanged] = useState(true);
  const [familyName, setFamilyName] = useState(family.name);
  const [nickname, setNickname] = useState(user.nickname);
  const [families, setFamilies] = useState(user.familyInfo);
  const [selectedFamilyId, setSelectedFamilyId] = useState(family.id);
  const [motto, setMotto] = useState(family.motto);
  const [invitationCode, setInvitationCode] = useState(family.invitationCode);
  const [newLeaderMemberId, setNewLeaderMemberId] = useState(null);
  const [isLeader, setIsLeader] = useState(false);
  const [familyMembers, setFamilyMembers] = useState([
    {
      memberId: 0,
      nickname: '',
      role: '',
      me: false,
    },
  ]);
  console.log(user);
  useEffect(() => {
    axios
      .get(`family/${family.id}/familyMembers`)
      .then((response) => {
        setFamilyMembers(response.data);
        for (const member of response.data) {
          if (member.role === 'LEADER' && member.me === true) {
            setIsLeader(true);
            break;
          }
        }
      })
      .catch((error) => {
        console.log(error);
      });
  }, [isChanged, isLeader]);

  const handleMandate = () => {
    axios
      .put(`/family/${family.id}/mandate`, {
        newLeaderMemberId: newLeaderMemberId,
      })
      .then((response) => {
        setIsChanged(!isChanged);
        setIsLeader(false);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleNameChange = (e) => {
    setFamilyName(e.target.value);
  };

  const handleMottoChange = (e) => {
    setMotto(e.target.value);
  };

  const handleNewLeader = (e) => {
    setNewLeaderMemberId(e.target.value);
  };

  const handleNicknameChange = (e) => {
    setNickname(e.target.value);
  };

  const handleNickname = (e) => {
    axios
      .put('/member', {
        newNickname: nickname,
      })
      .then((response) => {
        setIsChanged(!isChanged);
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const handleStart = () => {
    navigate(`/tree/${selectedFamilyId}`);
  };
  return (
    <>
      <h1>설정 페이지</h1>
      <Link to="/createtree">가족 생성하기</Link>
      {/* 가족 설정 탭 */}
      <div className="w-full h-5/6 m-auto pt-10">
        <div className="w-full flex justify-around mb-10 h-12">
          <div className="rounded-md px-4 w-full pl-4 flex justify-center items-center">
            {familyMembers.map((member) => (
              <>
                <div className="bg-gray-300 rounded-3xl px-2 mx-2">
                  {member.role === 'LEADER' ? '👑 ' : ''}
                  {member.nickname}
                </div>
              </>
            ))}
          </div>
        </div>

        <div className="w-full flex justify-around items-center mb-10 h-12">
          <p className="w-1/4">가족 이름</p>
          <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between h-full">
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
          <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between h-full">
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
          <div className="border-4 border-black rounded-md p-1 w-1/2 flex justify-between pl-4 h-full">
            <p className="w-5/6 truncate text-left">{invitationCode}</p>
            <CopyToClipboard text={invitationCode}>
              <button className="bg-gray-300 px-4 w-20">복사</button>
            </CopyToClipboard>
          </div>
        </div>
        {isLeader && (
          <div className="w-full flex justify-around items-center mb-10 h-12">
            <p className="w-1/4">가장 위임</p>
            <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between h-full">
              <select name="" className="w-5/6" onChange={handleNewLeader}>
                {/* <option value="" className="w-full"></option> */}
                {familyMembers.map((member, index) =>
                  member.role === 'LEADER' ? null : (
                    <>
                      <option value={member.memberId} className="w-full">
                        {member.nickname}
                      </option>
                    </>
                  )
                )}
              </select>
              <button onClick={handleMandate} className="bg-gray-300 px-4 w-20">
                위임
              </button>
            </div>
          </div>
        )}
        {/* </div> */}
        {/* 개인 설정 탭 */}
        {/* <div className="w-full h-5/6 m-auto pt-10"> */}
        {/* <div> */}
        <div className="w-full flex justify-around items-center mb-10 h-12">
          <p className="w-1/4">닉네임</p>
          <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between h-full">
            <input
              type="text"
              value={nickname}
              onChange={handleNicknameChange}
              className="w-5/6"
            />
            <button onClick={handleNickname} className="bg-gray-300 px-4 w-20">
              저장
            </button>
          </div>
        </div>
        <div className="w-full flex justify-around items-center mb-10 h-12">
          <p className="w-1/4">가족 선택</p>
          <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between h-full">
            <select
              id="family_select"
              value={selectedFamilyId}
              onChange={(e) => setSelectedFamilyId(e.target.value)}
              className="w-5/6"
            >
              {families.map((family, index) => (
                <option key={index} value={family.familyId} className="w-full">
                  {family.name}
                </option>
              ))}
            </select>
            <button onClick={handleStart} className="bg-gray-300 px-4 w-20">
              이동
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
