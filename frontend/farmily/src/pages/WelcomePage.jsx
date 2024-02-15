import { useSelector, useDispatch } from 'react-redux';
import axios from '../api/axios.jsx';

import sapling from '../assets/images/sapling.png';
import board from '../assets/images/mainboard.png';

import { useState, useEffect } from 'react';
import { getAccessToken } from '../store/auth.jsx';
import { getFamilies, getUser } from '../store/user.jsx';
import { Link, useNavigate } from 'react-router-dom';

const WelcomePage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const auth = useSelector((state) => state.auth.value);
  const user = useSelector((state) => state.user.value);

  const [families, setFamilies] = useState([]);
  const [selectedFamilyId, setSelectedFamilyId] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [error, setError] = useState('');

  const cookies = document.cookie.split(';');
  const cookie = cookies.find((c) => c.trim().startsWith('accessToken='));
  const accessToken = cookie.split('accessToken=')[1];

  useEffect(() => {
    dispatch(getAccessToken({ accessToken: accessToken }));

    axios
      .get('/member/me')
      .then((res) => {
        dispatch(getUser(res.data));
      })
      .catch((err) => {
        console.log(err);
      });

    axios
      .get('/member/family')
      .then((res) => {
        dispatch(getFamilies({ familyInfo: res.data }));
        setFamilies(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const handleStart = () => {
    if (!selectedFamilyId) {
      setError('가족을 선택해주세요.');
      return;
    }

    // 시작하기 버튼을 눌렀을 때 실행되는 로직 추가
    // 여기서 선택된 가족에 대한 추가적인 로직을 수행할 수 있습니다.
    closeModal(); // 모달 닫기
    navigate(`/tree/${selectedFamilyId}`);
  };

  return (
    <>
      <div className="w-2/6 mb-60 pl-36">
        <p className="text-3xl">Welcome Farmily</p>
        <p className="text-2xl">가족과 함께</p>
        <p className="text-2xl">새로운 나무를 키워보세요!</p>
        <br />
        <p>{'가족 나무를 심고'}</p>
        <p>{'소중한 추억을 기록하고 열매를 수확하세요.'}</p>
        <br />
      </div>
      <div className="w-5/12">
        <img className="mb-24 ml-24" src={sapling} alt="" />
      </div>
      <div className="w-1/6 mr-32 mb-28 relative">
        <p
          className="text-white text-4xl absolute top-14 left-12 cursor-pointer mt-3"
          onClick={openModal}
        >
          시작하기
        </p>
        <img src={board} alt="" />
      </div>

      {isModalOpen && (
        <div className="fixed z-10 inset-0 overflow-y-auto">
          <div className="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
            <div
              className="fixed inset-0 transition-opacity"
              aria-hidden="true"
            >
              <div className="absolute inset-0 bg-gray-800 opacity-75"></div>
            </div>
            <span
              className="hidden sm:inline-block sm:align-middle sm:h-screen"
              aria-hidden="true"
            >
              &#8203;
            </span>
            <div className="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
              <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                <div className="sm:flex sm:items-start">
                  <div className="w-1/2">
                    <h3 className="text-lg font-medium text-center">
                      가족 생성 / 참여
                    </h3>
                    <div className="flex flex-col items-center">
                      <p className="text-sm text-gray-500 mb-4 text-center">
                        가족을 생성하거나 참여하세요.
                      </p>
                      <Link to="/createtree">
                        <button className="bg-green-400 hover:bg-green-600 text-white font-medium py-2 px-4 rounded-md focus:outline-none focus:ring focus:border-blue-300">
                          가족 생성 / 참여
                        </button>
                      </Link>
                    </div>
                  </div>
                  <div className="w-1/2 mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                    <h3
                      className="text-lg leading-6 font-medium text-gray-900 text-center"
                      id="modal-title"
                    >
                      가족 선택
                    </h3>
                    <div className="">
                      <label
                        htmlFor="family_select"
                        className="block text-sm font-medium text-gray-700 mb-4 text-center"
                      >
                        가족을 선택하세요
                      </label>
                      {families.length ? (
                        <select
                          id="family_select"
                          value={selectedFamilyId}
                          onChange={(e) => setSelectedFamilyId(e.target.value)}
                          className="mt-1 p-2 w-full border border-gray-300 rounded-md focus:outline-none focus:ring focus:border-blue-300"
                        >
                          <option value="" id="disableOption" disabled selected>
                            가족을 선택해주세요.
                          </option>
                          {families.map((family, index) => (
                            <option key={index} value={family.familyId}>
                              {family.name}
                            </option>
                          ))}
                        </select>
                      ) : (
                        <p>새로운 가족을 만들어주세요</p>
                      )}
                    </div>
                  </div>
                </div>
              </div>
              {error ? (
                <p className=" text-red-400 text-center">{error}</p>
              ) : null}

              <div className="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:justify-center">
                <button
                  onClick={handleStart}
                  type="button"
                  className="mx-1 w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 text-base font-medium text-white focus:outline-none focus:ring sm:ml-3 sm:w-auto sm:text-sm bg-green-400 hover:bg-green-600"
                >
                  시작하기
                </button>
                <button
                  onClick={closeModal}
                  type="button"
                  className="mx-1 mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring focus:border-blue-300 sm:mt-0 sm:w-auto sm:text-sm"
                >
                  취소
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default WelcomePage;
